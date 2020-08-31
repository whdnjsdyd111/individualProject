package 게임만들어보기.테스트.현프로젝트테스트.오브젝트;

import javafx.scene.shape.Rectangle;

public interface Objects {
	String[] weapons = {
		"뼈다귀.png",		// V
		"도끼.png",		// V
		"곤봉.png",		// V
		"나뭇가지.png",	// V
		"죽도.png",		// V
		"나무배트.png",	// V
		"금속배트.png",	// V
		"골프채.png",		// V
		"일본도.png",		// V
		"레이저소드.png"	// V
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
			"사과.png",
			"키위.png",
			"딸기.png",
			"오렌지.png",
			"김밥.png",
			"주먹밥.png",
			"빵1.png",
			"빵2.png",
			"빵3.png",
			"피자.png",
			"햄버거.png",
			"베이컨.png",
			"치킨.png",
			"고기.png"
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
		"물.png",
		"사이다.png",
		"사이다2.png",
		"콜라.png",
		"환타.png"
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
