package com.inhand.milk.fragment.temperature_amount;

import java.util.List;

import com.example.aaaa.R;

import android.view.View;

public class TemperatureStatistics extends BaseStatistics{

	@Override
	protected void setTitle() {
		// TODO Auto-generated method stub
		String title =  getResources().getString(R.string.temperature_title_text);
		setTitleview(title, 1);
	}

	@Override
	protected void addPaperView(int width,List<View> listViews, List<OnePaper> onePapers) {
		// TODO Auto-generated method stub
		OnePaper paper;
		paper= new TemperatureDay(getActivity(), width,
							this.getActivity().getApplicationContext());
		onePapers.add(paper);
		listViews.add( paper.getPaper() );
		
		paper= new TemperatureWeek(getActivity(), width,
				this.getActivity().getApplicationContext());
		onePapers.add(paper);
		listViews.add( paper.getPaper() );
		
		paper= new TemperatureMonth(getActivity(), width,
				this.getActivity().getApplicationContext());
		onePapers.add(paper);
		listViews.add( paper.getPaper() );
	}

}
