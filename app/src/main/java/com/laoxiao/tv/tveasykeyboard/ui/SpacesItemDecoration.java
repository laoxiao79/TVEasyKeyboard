package com.laoxiao.tv.tveasykeyboard.ui;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int widespace;
        private int hightspace;

        public SpacesItemDecoration(int widespace, int hightspace) {
            this.widespace = widespace;
            this.hightspace = hightspace;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
        	int pos = parent.getChildLayoutPosition(view) ;

            outRect.left = widespace/2;
            outRect.right = widespace/2;
            outRect.bottom = 0;

            outRect.top = hightspace;
        }
    }