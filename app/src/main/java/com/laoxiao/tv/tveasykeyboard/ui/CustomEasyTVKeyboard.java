package com.laoxiao.tv.tveasykeyboard.ui;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.laoxiao.tv.tveasykeyboard.R;

/**
 * Created by laoxiao79 on 2017/6/27.
 */

public class CustomEasyTVKeyboard extends RelativeLayout {
    private static final String TAG = "CustomEasyTVKeyboard";
    private String searchText;

    private TextView tvSearchView;

    private RecyclerView rvKeyList;

    private LinearLayout tvRemoveAll;

    private LinearLayout tvDeleteOne;

    private MyAdapter mAdapter;

    private static String inputList[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","0","1","2","3","4","5","6","7","8","9"};

    public OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public OnMyTextChangedListener mOnMyTextChangedListener = null;
    //TextWatcher mTextWatcher = null;

    //保存实际存储的字符串
    public String actualInputText = "";
    //显示的字符串
    public String showText = "";

    public CustomEasyTVKeyboard(Context context) {
        this(context, null);
    }

    public CustomEasyTVKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomEasyTVKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs);
    }

    public void initView(Context context, AttributeSet attrs){
        View.inflate(context, R.layout.custom_input_board, this);

        tvSearchView = (TextView)findViewById(R.id.tv_input);
        rvKeyList = (RecyclerView)findViewById(R.id.rv_keylist);
        tvRemoveAll = (LinearLayout)findViewById(R.id.remove_all_icon);
        tvDeleteOne = (LinearLayout)findViewById(R.id.delete_icon);

        tvSearchView.addTextChangedListener(mTextWatcher);

        tvRemoveAll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                actualInputText = "";
                showText = "";
                tvSearchView.setText(showText);
            }
        });
        tvDeleteOne.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSearchText();
            }
        });

        //创建默认的线性LayoutManager
        GridLayoutManager layoutManager = new GridLayoutManager(context, 6);
        rvKeyList.setLayoutManager(layoutManager);


        // 根据布局管理器设置分割线
        int wideSpacingInPixels = getResources().getDimensionPixelSize(R.dimen.recycleview_item_spacing_20);
        int heightSpacingInPixels = getResources().getDimensionPixelSize(R.dimen.recycleview_item_spacing_30);

        rvKeyList.addItemDecoration(new SpacesItemDecoration(wideSpacingInPixels, heightSpacingInPixels));

        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvKeyList.setHasFixedSize(true);

        //创建并设置Adapter
        mAdapter = new MyAdapter(inputList);
        rvKeyList.setAdapter(mAdapter);

        mOnItemClickListener = new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Log.i(TAG, "-----onItemClick------actualInputText---1="+actualInputText);
                Log.i(TAG, "-----onItemClick------showText---1="+showText);
                Log.i(TAG, "-----onItemClick------tvSearchView.getText()---1="+tvSearchView.getText());
                actualInputText = actualInputText + data;
                showText = transferShowingText(actualInputText);
                tvSearchView.setText(showText);
                Log.i(TAG, "-----onItemClick------actualInputText---2="+actualInputText);
                Log.i(TAG, "-----onItemClick------showText---2="+showText);
                Log.i(TAG, "-----onItemClick------tvSearchView.getText()---2="+tvSearchView.getText());
            }
        };
        mAdapter.setOnItemClickListener(mOnItemClickListener);
    }

    public String getSearchText(){
        return actualInputText;
    }

    public void setSearchText(String search){
        actualInputText = search;
        showText = transferShowingText(actualInputText);
        tvSearchView.setText(showText);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(694, MeasureSpec.EXACTLY);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(480, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener{
        private boolean initFlag = true;
        public String[] datas = null;
        OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

        public MyAdapter(String[] datas) {
            this.datas = datas;
        }

        //创建新View
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.input_board_item, viewGroup, false);
            ViewHolder vh = new ViewHolder(view);

            //将创建的View注册点击事件
            view.setOnClickListener(this);
            return vh;
        }

        //将数据与界面进行绑定
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            viewHolder.mTextView.setText(datas[position]);

            //将数据保存在itemView的Tag中，以便点击时进行获取
            viewHolder.itemView.setTag(datas[position]);
            if(initFlag && position == 0){
                initFlag = false;
                viewHolder.itemView.requestFocus();
            }
        }

        //获取数据数量
        @Override
        public int getItemCount() {
            return datas.length;
        }

        @Override
        public void onClick(View v) {
            if (onRecyclerViewItemClickListener != null) {
                onRecyclerViewItemClickListener.onItemClick(v,(String)v.getTag());
            }
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;

            public ViewHolder(View view){
                super(view);
                mTextView = (TextView) view.findViewById(R.id.tv_textInput);
            }
        }

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            this.onRecyclerViewItemClickListener = listener;
        }
    }

    public TextWatcher getTextWatcher() {
        return mTextWatcher;
    }

    /*public void setTextWatcher(TextWatcher mTextWatcher) {
        this.mTextWatcher = mTextWatcher;
    }*/

    public static interface OnMyTextChangedListener {
        //void beforeTextChaned();
        void onTextChanged(String text);
    }

    public void setmOnMyTextChangedListener(OnMyTextChangedListener mOnMyTextChangedListener) {
        this.mOnMyTextChangedListener = mOnMyTextChangedListener;
    }

    TextWatcher mTextWatcher = new TextWatcher(){

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.i(TAG,"-------------beforeTextChanged mTextWatcher------------ s="+s);
            Log.i(TAG,"-------------beforeTextChanged mTextWatcher------------ start="+start+",count="+count+",after="+after);
            /*if(mOnMyTextChangedListener != null){
                mOnMyTextChangedListener.beforeTextChaned();
            }*/
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.i(TAG,"-------------onTextChanged mTextWatcher------------onTextChanged s="+s);
            Log.i(TAG,"-------------onTextChanged mTextWatcher------------ start="+start+",before="+before+",count="+count);
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.i(TAG,"-------------afterTextChanged mTextWatcher------------ s="+s.toString());
            if(mOnMyTextChangedListener != null){
                mOnMyTextChangedListener.onTextChanged(s.toString());
            }
        }
    };

    public void deleteSearchText(){
        if(actualInputText != null) {
            int len = actualInputText.length();
            Log.i(TAG, "------tvDeleteOne onClick-------len=" + len);
            Log.i(TAG, "------tvDeleteOne onClick-------actualInputText is:" + actualInputText);
            if (len > 1) {
                actualInputText = actualInputText.substring(0, len - 1);
                showText = transferShowingText(actualInputText);
                tvSearchView.setText(showText);
            } else {
                actualInputText = "";
                showText = "";
                tvSearchView.setText(showText);
            }
            Log.i(TAG, "------tvDeleteOne onClick-------after set actualInputText is:" + actualInputText);
            Log.i(TAG, "------tvDeleteOne onClick-------after set showText is:" + showText);
        }else{
            actualInputText = "";
            showText = "";
            tvSearchView.setText(showText);
        }
    }

    //将实际的字符串转换成“打点”的字符串进行显示，并且打点在字符串开头的位置
    private String transferShowingText(String actualText){
        Log.i(TAG, "------transferShowingText-------actualText=" + actualText);
        TextPaint textPaint = tvSearchView.getPaint();

        int paddingLeft = tvSearchView.getPaddingLeft();
        int paddingRight = tvSearchView.getPaddingRight();

        //打点即省略号的占位宽度
        int bufferWidth = (int)textPaint.getTextSize();
        Log.i(TAG, "------transferShowingText-------bufferWidth=" + bufferWidth);
        Log.i(TAG, "------transferShowingText-------tvSearchView.getWidth()=" + tvSearchView.getWidth());

        float availableTextWidth = tvSearchView.getWidth() - paddingLeft - paddingRight - bufferWidth;
        Log.i(TAG, "------transferShowingText-------availableTextWidth=" + availableTextWidth);
        String ellipsizeStr = (String) TextUtils.ellipsize(actualText, textPaint, availableTextWidth, TextUtils.TruncateAt.START);
        Log.i(TAG, "------transferShowingText-------ellipsizeStr=" + ellipsizeStr);
        return ellipsizeStr;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }
}
