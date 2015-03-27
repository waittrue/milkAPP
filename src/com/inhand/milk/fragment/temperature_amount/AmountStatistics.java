package com.inhand.milk.fragment.temperature_amount;

import java.util.List;

import com.example.aaaa.R;

import android.view.View;

public class AmountStatistics extends BaseStatistics{

	@Override
	protected void setTitle() {
		// TODO Auto-generated method stub
		String title =  getResources().getString(R.string.milk_title_text);
		setTitleview(title, 1);
	}

	@Override
	protected void addPaperView(int width,List<View> listViews, List<OnePaper> onePapers) {
		// TODO Auto-generated method stub
		OnePaper paper;
		paper= new AmountDay(getActivity(),
							this.getActivity().getApplicationContext(),width);
		onePapers.add(paper);
		listViews.add( paper.getPaper() );
		
		paper= new AmountWeek(getActivity(),
				this.getActivity().getApplicationContext() ,width);
		onePapers.add(paper);
		listViews.add( paper.getPaper() );
		
		paper= new AmountMonth(getActivity(),
				this.getActivity().getApplicationContext(),width);
		onePapers.add(paper);
		listViews.add( paper.getPaper() );
	}

}
