package info.fandroid.quizapp.quizapplication.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import info.fandroid.quizapp.quizapplication.R;
import info.fandroid.quizapp.quizapplication.constants.AppConstants;
import info.fandroid.quizapp.quizapplication.listeners.ListItemClickListener;
import info.fandroid.quizapp.quizapplication.models.quiz.ResultModel;


public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    private Context mContext;
    private Activity mActivity;

    private ArrayList<ResultModel> mItemList;
    private ListItemClickListener mItemClickListener;

    public ResultAdapter(Context mContext, Activity mActivity, ArrayList<ResultModel> mItemList) {
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mItemList = mItemList;
    }

    public void setItemClickListener(ListItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false);
        return new ViewHolder(view, viewType, mItemClickListener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imgAns;
        private TextView tvQuestion,tvImage, tvGivenAns, tvCorrectAns;
        private LinearLayout ans_container;
        private ListItemClickListener itemClickListener;
        private WebView image_ansv, q_web;



        public ViewHolder(View itemView, int viewType, ListItemClickListener itemClickListener) {
            super(itemView);

            this.itemClickListener = itemClickListener;
            // Find all views ids
            imgAns = (ImageView) itemView.findViewById(R.id.ans_icon);
            tvQuestion = (TextView) itemView.findViewById(R.id.question_text);
            tvImage = (TextView) itemView.findViewById(R.id.text);
            tvGivenAns = (TextView) itemView.findViewById(R.id.given_ans_text);
            tvCorrectAns = (TextView) itemView.findViewById(R.id.correct_ans_text);
            image_ansv = (WebView) itemView.findViewById(R.id.image_ans);
            q_web = (WebView) itemView.findViewById(R.id.question_web);
            ans_container = (LinearLayout) itemView.findViewById(R.id.ans_container) ;

            tvImage.setVisibility(View.GONE);
            tvQuestion.setVisibility(View.GONE);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(getLayoutPosition(), view);
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != mItemList ? mItemList.size() : 0);

    }

    @Override
    public void onBindViewHolder(ViewHolder mainHolder, int position) {
        final ResultModel model = mItemList.get(position);

        // setting data over views
        mainHolder.tvQuestion.setText((model.getQuestion()));
        String str = mainHolder.tvQuestion.getText().toString();
        mainHolder.q_web.loadUrl(str);
        mainHolder.tvCorrectAns.setText(Html.fromHtml(model.getCorrectAns()));
        mainHolder.tvCorrectAns.setText(Html.fromHtml(model.getCorrectAns()));

        if (model.isCorrect()) {
            mainHolder.ans_container.setVisibility(View.GONE);
        } else {
            mainHolder.tvGivenAns.setText(Html.fromHtml(model.getGivenAns()));

            //mainHolder.image_ans.loadUrl(WebView.findAddress(model.getGivenAns()));
            mainHolder.tvImage.setText((model.getAns_all()));




            String strName = mainHolder.tvImage.getText().toString();
            mainHolder.image_ansv.loadUrl(strName);
        }

        int imgPosition;
        if (model.isSkip()) {
            imgPosition = AppConstants.BUNDLE_KEY_ZERO_INDEX;
        } else if (model.isCorrect()) {
            imgPosition = AppConstants.BUNDLE_KEY_FIRST_INDEX;
        } else {
            imgPosition = AppConstants.BUNDLE_KEY_SECOND_INDEX;
        }

        Glide.with(mContext)
                .load(mContext.getResources().getIdentifier(AppConstants.DIRECTORY + imgPosition, null, mContext.getPackageName()))
                .into(mainHolder.imgAns);

    }
}
