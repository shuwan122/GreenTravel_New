package com.example.zero.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zero.adapter.GoodsAdapter;
import com.example.zero.adapter.SelectAdapter;
import com.example.zero.adapter.TypeAdapter;
import com.example.zero.entity.GoodsItem;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.HttpUtil;
import com.example.zero.view.DividerDecoration;
import com.example.zero.view.TitleShopLayout;
import com.flipboard.bottomsheet.BottomSheetLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Response;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import static com.example.zero.fragment.RouteFragmentDouble.Origin.ADVICE;

public class ShoppingCartActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgCart;
    private ViewGroup anim_mask_layout;
    private RecyclerView rvType, rvSelected;
    private TextView tvCount, tvCost, tvSubmit, tvTips;
    private BottomSheetLayout bottomSheetLayout;
    private View bottomSheet;
    private StickyListHeadersListView listView;

    private TitleShopLayout title;
    private String shopId;
    private String shopImg;
    private String shopName;

    private Context context;

    private ArrayList<GoodsItem> dataList, typeList;
    private SparseArray<GoodsItem> selectedList;
    private SparseIntArray groupSelect;

    private GoodsAdapter myAdapter;
    private SelectAdapter selectAdapter;
    private TypeAdapter typeAdapter;

    private NumberFormat nf;
    private Handler mHanlder;

    //前后端接口
    private ArrayList<String> selectIdList = new ArrayList<String>();

    private int size = 100;
    private String[] idList = new String[size];
    private String[] goodsTypeList = new String[size];
    private String[] nameList = new String[size];
    private String[] posterList = new String[size];
    private double[] priceList = new double[size];
    private String[] descriptionList = new String[size];
    private int[] numList = new int[size];
    private String[] sellerIdList = new String[size];
    private int[] buyNumList = new int[size];

    private Button mCoupon;

    private static final String TAG = "ShoppingCartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        mHanlder = new Handler(getMainLooper());
        dataList = GoodsItem.getGoodsList();
        typeList = GoodsItem.getTypeList();
        selectedList = new SparseArray<>();
        groupSelect = new SparseIntArray();

        context = getBaseContext();
        Intent intent = getIntent();
        shopId = intent.getStringExtra("shopId");
        shopName = intent.getStringExtra("shopName");
        shopImg = intent.getStringExtra("shopImg");
        String bracket = shopName.substring(shopName.indexOf("（"), shopName.indexOf("）") + 1);
        shopName = shopName.replace(bracket, "");

        // TODO: 2017/11/10 商品初始化
        final Bundle mBundle = new Bundle();
        mBundle.putString("userId", "guest");
        mBundle.putString("shopId", shopId);
        HttpUtil.sendGoodsOkHttpRequest(mBundle, new okhttp3.Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                parseJSONWithJSONObject(responseData);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ERROR!");
                Toast.makeText(context, "连接服务器失败，请重新尝试！", Toast.LENGTH_LONG).show();
            }
        });

        title = (TitleShopLayout) findViewById(R.id.shop_title);
        title.setText(shopName);
        title.setImg(context, shopImg);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void parseJSONWithJSONObject(String jsonData) {
        try {
            int count = 0;
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray goods = jsonObject.getJSONArray("goodsInfo");

            if (goods.length() > 0) {
                for (int i = 0; i < goods.length(); i++) {
                    idList[count] = goods.getJSONObject(i).getString("id");
                    goodsTypeList[count] = goods.getJSONObject(i).getString("goods_type");
                    nameList[count] = goods.getJSONObject(i).getString("goods_name");
                    posterList[count] = goods.getJSONObject(i).getString("picture_url");
                    priceList[count] = goods.getJSONObject(i).getDouble("price");
                    descriptionList[count] = goods.getJSONObject(i).getString("description");
                    numList[count] = goods.getJSONObject(i).getInt("goods_number");
                    sellerIdList[count] = goods.getJSONObject(i).getString("seller_id");
//                    buyNumList[count] = goods.getJSONObject(i).getInt("bought_num");
                    count++;
                }
            }

            Set<String> set = new HashSet<>();
            for (int i = 0; i < count; i++) {
                set.add(goodsTypeList[i]);
            }
            String[] typeResult = (String[]) set.toArray(new String[set.size()]);

            ArrayList<GoodsItem> goodHandleList = new ArrayList<GoodsItem>();
            ArrayList<GoodsItem> typeHandleList = new ArrayList<GoodsItem>();
            GoodsItem item = null;

            for (int i = 0; i < typeResult.length; i++) {
                for (int j = 0; j < count; j++) {
                    if (goodsTypeList[j].equals(typeResult[i])) {
                        item = new GoodsItem(Integer.valueOf(idList[j]), priceList[j], nameList[j], i, typeResult[i], posterList[j]);
                        goodHandleList.add(item);
                    }
                }
                typeHandleList.add(item);
            }

            dataList = goodHandleList;
            typeList = typeHandleList;
            selectedList = new SparseArray<>();
            groupSelect = new SparseIntArray();

            initView();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        tvCount = (TextView) findViewById(R.id.tvCount);
        tvCost = (TextView) findViewById(R.id.tvCost);
        tvTips = (TextView) findViewById(R.id.tvTips);
        tvSubmit = (TextView) findViewById(R.id.tvSubmit);
        rvType = (RecyclerView) findViewById(R.id.typeRecyclerView);

        imgCart = (ImageView) findViewById(R.id.imgCart);
        anim_mask_layout = (RelativeLayout) findViewById(R.id.containerLayout);
        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomSheetLayout);

        listView = (StickyListHeadersListView) findViewById(R.id.itemListView);

        title = (TitleShopLayout) findViewById(R.id.shop_title);
        title.setText(shopName);
        title.setImg(context, shopImg);

        rvType.setLayoutManager(new LinearLayoutManager(this));
        typeAdapter = new TypeAdapter(this, typeList);
        rvType.setAdapter(typeAdapter);
        rvType.addItemDecoration(new DividerDecoration(this));

        myAdapter = new GoodsAdapter(dataList, this);
        listView.setAdapter(myAdapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                GoodsItem item = dataList.get(firstVisibleItem);
                if (typeAdapter.selectTypeId != item.typeId) {
                    typeAdapter.selectTypeId = item.typeId;
                    typeAdapter.notifyDataSetChanged();
                    rvType.smoothScrollToPosition(getSelectedGroupPosition(item.typeId));
                }
            }
        });

        mCoupon = (Button) findViewById(R.id.shoppingcart_coupon);
        mCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle mBundle = new Bundle();
                Intent intent = new Intent(context, NearShopCouponActivity.class);
                mBundle.putString("shopId", shopId);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
    }

    public void playAnimation(int[] start_location) {
        ImageView img = new ImageView(this);
        img.setImageResource(R.drawable.button_add);
        setAnim(img, start_location);
    }

    private Animation createAnim(int startX, int startY) {
        int[] des = new int[2];
        imgCart.getLocationInWindow(des);

        AnimationSet set = new AnimationSet(false);

        Animation translationX = new TranslateAnimation(0, des[0] - startX, 0, 0);
        translationX.setInterpolator(new LinearInterpolator());
        Animation translationY = new TranslateAnimation(0, 0, 0, des[1] - startY);
        translationY.setInterpolator(new AccelerateInterpolator());
        Animation alpha = new AlphaAnimation(1, 0.5f);
        set.addAnimation(translationX);
        set.addAnimation(translationY);
        set.addAnimation(alpha);
        set.setDuration(500);

        return set;
    }

    private void addViewToAnimLayout(final ViewGroup vg, final View view, int[] location) {
        int x = location[0];
        int y = location[1];
        int[] loc = new int[2];
        vg.getLocationInWindow(loc);
        view.setX(x);
        view.setY(y - loc[1]);
        vg.addView(view);
    }

    private void setAnim(final View v, int[] start_location) {

        addViewToAnimLayout(anim_mask_layout, v, start_location);
        Animation set = createAnim(start_location[0], start_location[1]);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                mHanlder.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        anim_mask_layout.removeView(v);
                    }
                }, 100);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(set);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom:
                showBottomSheet();
                break;
            case R.id.clear:
                clearCart();
                break;
            case R.id.tvSubmit:
                int size = selectedList.size();
                String[] idList = new String[size];
                String[] nameList = new String[size];
                String[] posterList = new String[size];
                double[] priceList = new double[size];
                int[] numList = new int[size];

                for (int i = 0; i < size; i++) {
                    idList[i] = String.valueOf(selectedList.get(Integer.valueOf(selectIdList.get(i))).id);
                    nameList[i] = selectedList.get(Integer.valueOf(selectIdList.get(i))).name;
                    posterList[i] = selectedList.get(Integer.valueOf(selectIdList.get(i))).imgUrl;
                    priceList[i] = selectedList.get(Integer.valueOf(selectIdList.get(i))).price;
                    numList[i] = selectedList.get(Integer.valueOf(selectIdList.get(i))).count;
                }

                Bundle mBundle = new Bundle();
                Intent intent = new Intent(context, ShopOrderActivity.class);
                mBundle.putString("shopName", shopName);
                mBundle.putString("shopId", shopId);
                mBundle.putInt("size", size);
                mBundle.putStringArray("idList", idList);
                mBundle.putStringArray("nameList", nameList);
                mBundle.putStringArray("posterList", posterList);
                mBundle.putDoubleArray("priceList", priceList);
                mBundle.putIntArray("numList", numList);

                intent.putExtras(mBundle);
                startActivity(intent);
                Toast.makeText(context, "结算", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    //添加商品
    public void add(GoodsItem item, boolean refreshGoodList) {

        int groupCount = groupSelect.get(item.typeId);
        if (groupCount == 0) {
            groupSelect.append(item.typeId, 1);
        } else {
            groupSelect.append(item.typeId, ++groupCount);
        }

        GoodsItem temp = selectedList.get(item.id);
        if (temp == null) {
            item.count = 1;
            selectIdList.add(String.valueOf(item.id));
            selectedList.append(item.id, item);
        } else {
            temp.count++;
        }
        update(refreshGoodList);
    }

    //移除商品
    public void remove(GoodsItem item, boolean refreshGoodList) {

        int groupCount = groupSelect.get(item.typeId);
        if (groupCount == 1) {
            groupSelect.delete(item.typeId);
        } else if (groupCount > 1) {
            groupSelect.append(item.typeId, --groupCount);
        }

        GoodsItem temp = selectedList.get(item.id);
        if (temp != null) {
            if (temp.count < 2) {
                selectedList.remove(item.id);
            } else {
                item.count--;
            }
        }
        update(refreshGoodList);
    }

    //刷新布局 总价、购买数量等
    private void update(boolean refreshGoodList) {
        int size = selectedList.size();
        int count = 0;
        double cost = 0;
        for (int i = 0; i < size; i++) {
            GoodsItem item = selectedList.valueAt(i);
            count += item.count;
            cost += item.count * item.price;
        }

        if (count < 1) {
            tvCount.setVisibility(View.GONE);
        } else {
            tvCount.setVisibility(View.VISIBLE);
        }

        tvCount.setText(String.valueOf(count));

        // TODO: 2017/11/10 最低起送价格
        if (cost > 99.99) {
            tvTips.setVisibility(View.GONE);
            tvSubmit.setVisibility(View.VISIBLE);
        } else {
            tvSubmit.setVisibility(View.GONE);
            tvTips.setVisibility(View.VISIBLE);
        }

        tvCost.setText(nf.format(cost));

        if (myAdapter != null && refreshGoodList) {
            myAdapter.notifyDataSetChanged();
        }
        if (selectAdapter != null) {
            selectAdapter.notifyDataSetChanged();
        }
        if (typeAdapter != null) {
            typeAdapter.notifyDataSetChanged();
        }
        if (bottomSheetLayout.isSheetShowing() && selectedList.size() < 1) {
            bottomSheetLayout.dismissSheet();
        }
    }

    //清空购物车
    public void clearCart() {
        selectedList.clear();
        groupSelect.clear();
        update(true);

    }

    //根据商品id获取当前商品的采购数量
    public int getSelectedItemCountById(int id) {
        GoodsItem temp = selectedList.get(id);
        if (temp == null) {
            return 0;
        }
        return temp.count;
    }

    //根据类别Id获取属于当前类别的数量
    public int getSelectedGroupCountByTypeId(int typeId) {
        return groupSelect.get(typeId);
    }

    //根据类别id获取分类的Position 用于滚动左侧的类别列表
    public int getSelectedGroupPosition(int typeId) {
        for (int i = 0; i < typeList.size(); i++) {
            if (typeId == typeList.get(i).typeId) {
                return i;
            }
        }
        return 0;
    }

    public void onTypeClicked(int typeId) {
        listView.setSelection(getSelectedPosition(typeId));
    }

    private int getSelectedPosition(int typeId) {
        int position = 0;
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).typeId == typeId) {
                position = i;
                break;
            }
        }
        return position;
    }

    private View createBottomSheetView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_bottom_sheet, (ViewGroup) getWindow().getDecorView(), false);
        rvSelected = (RecyclerView) view.findViewById(R.id.selectRecyclerView);
        rvSelected.setLayoutManager(new LinearLayoutManager(this));
        TextView clear = (TextView) view.findViewById(R.id.clear);
        clear.setOnClickListener(this);
        selectAdapter = new SelectAdapter(this, selectedList);
        rvSelected.setAdapter(selectAdapter);
        return view;
    }

    private void showBottomSheet() {
        if (bottomSheet == null) {
            bottomSheet = createBottomSheetView();
        }
        if (bottomSheetLayout.isSheetShowing()) {
            bottomSheetLayout.dismissSheet();
        } else {
            if (selectedList.size() != 0) {
                bottomSheetLayout.showWithSheetView(bottomSheet);
            }
        }
    }
}
