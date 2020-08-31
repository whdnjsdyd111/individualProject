package 게임만들어보기.로그라이크;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RandomMap extends Rectangle {
	List<Rectangle> list = new ArrayList<>();;
	AnchorPane Ap;

	public RandomMap(AnchorPane Ap) {
		this.Ap = Ap;
	}

	public List<Rectangle> BorderLine(int area) {
		final double WID = Ap.getPrefWidth();
		final double HEI = Ap.getPrefHeight();
		
		final int WTile = (int) WID / 100;
		final int HTile = (int) HEI / 100;
		
		for(int i = 0; i < HTile; i++) {
			for (int j = 0; j < WTile; j++) {
				Rectangle rect = new Rectangle();
				rect.setWidth(area);
				rect.setHeight(area);
				rect.setStroke(Color.BLUE);
				rect.setFill(Color.WHITE);
				if(i == 0 || i == HTile - 1 || j == 0 || j == WTile - 1)
					rect.setStroke(Color.RED);
				rect.setLayoutX(j * area);
				rect.setLayoutY(i * area);
				list.add(rect);
			}
		}
		
		Rectangle rect = new Rectangle();
		rect.setWidth(20);
		rect.setHeight(4);
		rect.setStroke(Color.GREEN);
		rect.setFill(Color.GREEN);
		rect.setLayoutX(210);
		rect.setLayoutY(210);
		
		list.add(rect);
		
		rect = new Rectangle();
		rect.setWidth(30);
		rect.setHeight(4);
		rect.setStroke(Color.GREEN);
		rect.setFill(Color.GREEN);
		rect.setLayoutX(400);
		rect.setLayoutY(200);
		
		list.add(rect);
		
		rect = new Rectangle();
		rect.setWidth(10);
		rect.setHeight(10);
		rect.setStroke(Color.BLACK);
		rect.setFill(Color.BLUE);
		rect.setLayoutX(445);
		rect.setLayoutY(295);
		
		list.add(rect);
		
		rect = new Rectangle();
		rect.setWidth(10);
		rect.setHeight(10);
		rect.setStroke(Color.GRAY);
		rect.setFill(Color.GRAY);
		rect.setLayoutX(300);
		rect.setLayoutY(300);
		
		list.add(rect);
		
		rect = new Rectangle();
		rect.setWidth(10);
		rect.setHeight(10);
		rect.setStroke(Color.GRAY);
		rect.setFill(Color.GRAY);
		rect.setLayoutX(312);
		rect.setLayoutY(300);
		
		list.add(rect);
		
		rect = new Rectangle();
		rect.setWidth(10);
		rect.setHeight(10);
		rect.setStroke(Color.GRAY);
		rect.setFill(Color.GRAY);
		rect.setLayoutX(350);
		rect.setLayoutY(310);
		
		list.add(rect);
		
		return list;

	}
}
