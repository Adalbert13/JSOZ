package com.adalbert.JSOZ.view.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsPanelInfo {

	private String title;
	private List<String> categories;
	private Map<Integer, List<String>> categoriesValues;
	
	public DetailsPanelInfo() {
		categories = new ArrayList<>();
		categoriesValues = new HashMap<>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void addCategoryWithValues(String categoryName, List<String> categoryValues) {
		categories.add(categoryName);
		categoriesValues.put(categories.size() - 1, categoryValues);
	}
	
	public void addCategoryWithValue(String categoryName, String categoryValue) {
		categories.add(categoryName);
		categoriesValues.put(categories.size() - 1, Arrays.asList(new String[] {categoryValue}));
	}
	
	public void addCategoryValue(int categoryIndex, String categoryValue) {
		if (categoriesValues.containsKey(categoryIndex))
			categoriesValues.get(categoryIndex).add(categoryValue);
		else throw new IllegalArgumentException("Wrong category index!");
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	
	public String getCategoryName(int categoryIndex) {
		return categories.get(categoryIndex);
	}

	public List<String> getCategoriesValues(int categoryIndex) {
		return categoriesValues.get(categoryIndex);
	}

	public void setCategoriesValues(Map<Integer, List<String>> categoriesValues) {
		this.categoriesValues = categoriesValues;
	}
	
	
	
}
