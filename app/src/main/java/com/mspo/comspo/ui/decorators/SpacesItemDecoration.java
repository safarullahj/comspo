package com.mspo.comspo.ui.decorators;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private static boolean _topPadding = false;
    private static boolean _bottomPadding = false;

    private int halfSpace;
    private int space;

    private int halfTopPadding;
    private int halfBottomPadding;

    public SpacesItemDecoration(Context context, @DimenRes int spacingDimen,
                                @DimenRes int topPadding, @DimenRes int bottomPadding) {
        space = context.getResources().getDimensionPixelSize(spacingDimen);
        this.halfSpace = space / 2;

        int topPadding1 = context.getResources().getDimensionPixelSize(topPadding);
        int bottomPadding1 = context.getResources().getDimensionPixelSize(bottomPadding);

        this.halfBottomPadding = bottomPadding1 / 2;
        this.halfTopPadding = topPadding1 / 2;

        _topPadding = true;
        _bottomPadding = true;
    }

    public SpacesItemDecoration(Context context, @DimenRes int spacingDimen) {
        space = context.getResources().getDimensionPixelSize(spacingDimen);
        this.halfSpace = space / 2;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getPaddingLeft() != halfSpace) {
            if (_topPadding && _bottomPadding) {
                parent.setPadding(halfSpace, halfTopPadding, halfSpace, halfBottomPadding);
            } else {
                parent.setPadding(halfSpace, halfSpace, halfSpace, halfSpace);
            }
            parent.setClipToPadding(false);
        }

        outRect.top = halfSpace;
        outRect.bottom = halfSpace;
        outRect.left = halfSpace;
        outRect.right = halfSpace;
    }
}