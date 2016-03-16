package com.infy.lbsprototype.model;

import java.util.List;

public class WeatherInfo {
	
	private List<Weather> weather;
	private Temperature main;
	private Wind wind;
	private String name;
	
	public Wind getWind() {
		return wind;
	}
	public void setWind(Wind wind) {
		this.wind = wind;
	}
	public List<Weather> getWeather() {
		return weather;
	}
	public void setWeather(List<Weather> weather) {
		this.weather = weather;
	}
	public Temperature getMain() {
		return main;
	}
	public void setMain(Temperature main) {
		this.main = main;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
