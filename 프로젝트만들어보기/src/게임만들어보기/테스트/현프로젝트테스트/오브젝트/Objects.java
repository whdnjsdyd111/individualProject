package ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.������Ʈ;

import javafx.scene.shape.Rectangle;

public interface Objects {
	String[] weapons = {
		"���ٱ�.png",		// V
		"����.png",		// V
		"���.png",		// V
		"��������.png",	// V
		"�׵�.png",		// V
		"������Ʈ.png",	// V
		"�ݼӹ�Ʈ.png",	// V
		"����ä.png",		// V
		"�Ϻ���.png",		// V
		"�������ҵ�.png"	// V
	};
	
	int[][] weightHeightWeapon = {
			{ 20, 7 },
			{ 23, 8 },
			{ 20, 10 },
			{ 30, 6 },
			{ 30, 5 },
			{ 25, 5 },
			{ 30, 5 },
			{ 37, 10 },
			{ 47, 7 },
			{ 45, 5 }
	};
	
	String[] foods = {
			"���.png",
			"Ű��.png",
			"����.png",
			"������.png",
			"���.png",
			"�ָԹ�.png",
			"��1.png",
			"��2.png",
			"��3.png",
			"����.png",
			"�ܹ���.png",
			"������.png",
			"ġŲ.png",
			"���.png"
		};
		
	int[][] weightHeightFood = {
			{10, 10},
			{10, 10},
			{10, 10},
			{10, 10},
			{10, 10},
			{10, 10},
			{10, 10},
			{10, 10},
			{10, 10},
			{10, 10},
			{10, 10},
			{10, 10},
			{10, 10},
			{10, 10}
	};
	
	String[] waters = {
		"��.png",
		"���̴�.png",
		"���̴�2.png",
		"�ݶ�.png",
		"ȯŸ.png"
	};
	
	int[][] weightHeightWater = {
		{5, 12},
		{5, 11},
		{5, 12},
		{5, 12},
		{5, 12}
	};
	
	public Rectangle makeObject(int num);
	
	default public boolean checkIn(double x1, double y1, double x2, double y2, Rectangle rect) {
		return ((rect.getLayoutX() < x1 && x1 < rect.getLayoutX() + rect.getWidth())	&&
				(rect.getLayoutX() < x2 && x2 < rect.getLayoutX() + rect.getWidth()))	&&
				((rect.getLayoutY() < y1 && y1 < rect.getLayoutY() + rect.getHeight())	&&
				(rect.getLayoutY() < y2 && y2 < rect.getLayoutY() + rect.getHeight()));
	}
	
	default public boolean checkMid(double x1, double y1, double x2, double y2, Rectangle rect) {
		return ((rect.getLayoutX() < x1 && x1 < rect.getLayoutX() + rect.getWidth())	||
				(rect.getLayoutX() < x2 && x2 < rect.getLayoutX() + rect.getWidth()))	&&
				((rect.getLayoutY() < y1 && y1 < rect.getLayoutY() + rect.getHeight())	||
				(rect.getLayoutY() < y2 && y2 < rect.getLayoutY() + rect.getHeight()));
	}
}
