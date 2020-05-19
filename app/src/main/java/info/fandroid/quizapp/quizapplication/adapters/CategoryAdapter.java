package info.fandroid.quizapp.quizapplication.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import info.fandroid.quizapp.quizapplication.R;
import info.fandroid.quizapp.quizapplication.activity.QuizPromptActivity;
import info.fandroid.quizapp.quizapplication.constants.AppConstants;
import info.fandroid.quizapp.quizapplication.data.preference.AppPreference;
import info.fandroid.quizapp.quizapplication.listeners.ListItemClickListener;
import info.fandroid.quizapp.quizapplication.models.quiz.CategoryModel;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context mContext;
    private Activity mActivity;
    private String categoryId, score, questionsCount;
    private double p=0,pa=0, ans;
    private ArrayList<CategoryModel> categoryList;
    private ListItemClickListener itemClickListener;



    public CategoryAdapter(Context mContext, Activity mActivity, ArrayList<CategoryModel> categoryList) {
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.categoryList = categoryList;


    }

    public void setItemClickListener(ListItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_recycler, parent, false);
        return new ViewHolder(view, viewType, itemClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        final CategoryModel model = categoryList.get(position);


        String categoryName = model.getCategoryName();
        //holder.tvCategoryTitle.setText(Html.fromHtml(categoryName));
        position =position+1;

        String s =Integer.toString(position);



        score = AppPreference.getInstance(mContext).getString(s);
        questionsCount = AppPreference.getInstance(mContext).getString(s + AppConstants.QUESTIONS_IN_TEST);
        if(score==null && questionsCount==null) {
             ans =0;
        }
        else {
            p = Double.parseDouble(score);
            pa = Double.parseDouble(questionsCount);
            ans = (p * 100) / pa;
        }

        if(ans<50 && ans!=0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_red_stroke));
        else if(ans>=50 && ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_orendg_stroke));
        else if(ans>80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_green_stroke));
        else if(ans==0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_white_stroke));

        holder.tvCategoryId.setText(String.valueOf(position));
/*
        switch (position) {
            case 0:
                if(ans<50 && ans!=0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_red_stroke));
        else if(ans>=50 && ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_orendg_stroke));
        else if(ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_green_stroke));
        else if(ans==0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_white_stroke));
                break;
            case 1:
                if(ans<50 && ans!=0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_red_stroke));
        else if(ans>=50 && ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_orendg_stroke));
        else if(ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_green_stroke));
        else if(ans==0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_white_stroke));
                break;
            case 2:
               if(ans<50 && ans!=0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_red_stroke));
        else if(ans>=50 && ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_orendg_stroke));
        else if(ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_green_stroke));
        else if(ans==0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_white_stroke));
                break;
            case 3:
                if(ans<50 && ans!=0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_red_stroke));
        else if(ans>=50 && ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_orendg_stroke));
        else if(ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_green_stroke));
        else if(ans==0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_white_stroke));
                break;
            case 4:
                if(ans<50 && ans!=0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_red_stroke));
        else if(ans>=50 && ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_orendg_stroke));
        else if(ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_green_stroke));
        else if(ans==0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_white_stroke));
                break;
            case 5:
                if(ans<50 && ans!=0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_red_stroke));
        else if(ans>=50 && ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_orendg_stroke));
        else if(ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_green_stroke));
        else if(ans==0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_white_stroke));
                break;
            case 6:
                if(ans<50 && ans!=0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_red_stroke));
        else if(ans>=50 && ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_orendg_stroke));
        else if(ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_green_stroke));
        else if(ans==0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_white_stroke));
                break;
            case 7:
                if(ans<50 && ans!=0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_red_stroke));
        else if(ans>=50 && ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_orendg_stroke));
        else if(ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_green_stroke));
        else if(ans==0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_white_stroke));
                break;
            case 8:
                if(ans<50 && ans!=0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_red_stroke));
        else if(ans>=50 && ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_orendg_stroke));
        else if(ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_green_stroke));
        else if(ans==0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_white_stroke));
                break;
            case 9:
                if(ans<50 && ans!=0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_red_stroke));
        else if(ans>=50 && ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_orendg_stroke));
        else if(ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_green_stroke));
        else if(ans==0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_white_stroke));
                break;
            case 10:
                if(ans<50 && ans!=0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_red_stroke));
        else if(ans>=50 && ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_orendg_stroke));
        else if(ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_green_stroke));
        else if(ans==0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_white_stroke));
                break;
            case 11:
                if(ans<50 && ans!=0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_red_stroke));
        else if(ans>=50 && ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_orendg_stroke));
        else if(ans<80)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_green_stroke));
        else if(ans==0)
            holder.tvCategoryId.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circular_white_stroke));
                break;
        }*/

    }




    @Override
    public int getItemCount() {
        return (null != categoryList ? categoryList.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RelativeLayout lytContainer;
        private TextView  tvCategoryId;
        //private TextView tvCategoryTitle;
        private ListItemClickListener itemClickListener;

        public ViewHolder(View itemView, int viewType, ListItemClickListener itemClickListener) {
            super(itemView);

            this.itemClickListener = itemClickListener;
            lytContainer = (RelativeLayout) itemView.findViewById(R.id.lytContainer);
            tvCategoryId = (TextView) itemView.findViewById(R.id.categoryId);
            //tvCategoryTitle = (TextView) itemView.findViewById(R.id.titleText);

            lytContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(getLayoutPosition(), view);
            }

        }
    }
}
