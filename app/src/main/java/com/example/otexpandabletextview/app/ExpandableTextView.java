package com.example.otexpandabletextview.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by cgould on 3/31/14.
 */
public class ExpandableTextView extends RelativeLayout implements Animation.AnimationListener {

	private TextView titleBarWhenCollapsed;
	private TextView titleBarWhenExpanded;
	private TextView expandedView;
	private Animation animationSlideDown;
	private Animation animationSlideUp;
	private Animation animationFadeIn;
	private Animation animationFadeOut;
	private boolean collapsing = false;
	private int titleColor;
	private String collapsedTitleText;
	private String expandedTitleText;

	public ExpandableTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

		initLayout(context);
		initAnimation(context);
		initOptions(context, attrs);
		initViews();
	}

	private void initLayout(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.expandable_text_view, this, true);
	}

	private void initAnimation(Context context) {
		animationSlideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
		animationSlideDown.setAnimationListener(this);

		animationSlideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
		animationSlideUp.setAnimationListener(this);

		animationFadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
		animationFadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);
	}

	private void initOptions(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextOptions, 0, 0);
		collapsedTitleText = a.getString(R.styleable.ExpandableTextOptions_collapsed_title_text);
		expandedTitleText = a.getString(R.styleable.ExpandableTextOptions_expanded_title_text);
		titleColor = a.getColor(R.styleable.ExpandableTextOptions_title_color, android.R.color.holo_blue_light);
		a.recycle();
	}

	private void initViews() {

		titleBarWhenCollapsed = (TextView) getChildAt(0);
		titleBarWhenCollapsed.setText(collapsedTitleText);
		titleBarWhenCollapsed.setTextColor(titleColor);
		titleBarWhenCollapsed.setVisibility(View.VISIBLE);

		titleBarWhenExpanded = (TextView) getChildAt(1);
		titleBarWhenExpanded.setText(expandedTitleText);
		titleBarWhenExpanded.setTextColor(titleColor);
		titleBarWhenExpanded.setVisibility(View.INVISIBLE);

		expandedView = (TextView) getChildAt(2);
		expandedView.setText("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		expandedView.setVisibility(View.GONE);

		setClickHandlers();
	}

	private void setClickHandlers() {
		titleBarWhenCollapsed.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toggleView();
			}
		});

		titleBarWhenExpanded.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				toggleView();
			}
		});
	}

	private void toggleView() {
		boolean collapsed = titleBarWhenCollapsed.getVisibility() == View.VISIBLE;
		if ( collapsed ) {
			titleBarWhenCollapsed.startAnimation(animationFadeOut);
			titleBarWhenExpanded.startAnimation(animationFadeIn);
			expandedView.setVisibility(View.VISIBLE);
			expandedView.startAnimation(animationSlideDown);
		} else {
			collapsing = true;
			titleBarWhenCollapsed.startAnimation(animationFadeIn);
			titleBarWhenExpanded.startAnimation(animationFadeOut);
			expandedView.startAnimation(animationSlideUp);
		}
	}


	@Override
	public void onAnimationStart(Animation animation) {

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if ( collapsing ) {
			expandedView.setVisibility(View.GONE);
			titleBarWhenExpanded.setVisibility(View.INVISIBLE);
			titleBarWhenCollapsed.setVisibility(View.VISIBLE);
			collapsing = false;
		} else {
			titleBarWhenExpanded.setVisibility(View.VISIBLE);
			titleBarWhenCollapsed.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}
}
