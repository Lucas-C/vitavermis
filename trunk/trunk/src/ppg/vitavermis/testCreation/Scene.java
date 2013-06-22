package ppg.vitavermis.testCreation;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import ppg.vitavermis.config.staticfield.Param;
import ppg.vitavermis.items.*;
import org.newdawn.slick.SlickException;


/**
 * Fichier test pour chargement d'objets
 * @author parpaing
 * @version 1.01
 *
 */

public final class Scene {
	ArrayList<Item> itemsList;
	@Param static int windowWidth;
	@Param static int windowHeight;
	
	public ArrayList<Item> getItemsList() {
		return itemsList;
	}

	public void init() throws SlickException {
		itemsList = new ArrayList<Item>();
		// hero
		MainCharacterItem hero = new MainCharacterItem(new Image("data/rond.png"));
		hero.setPosition(new Vector2f(350,350));
		itemsList.add(hero);
		// Mur droit
		Background murD = new Background(new Image("data/rectangle.png") , new String("Mur droit") );
		murD.setHeight(windowHeight);
		murD.setWidth(10);
		murD.setPosition(new Vector2f(windowWidth - 10, 0));
		itemsList.add(murD);
		// Mur gauche
		Background murG = new Background(new Image("data/rectangle.png") , new String("Mur gauche") );
		murG.setHeight(windowHeight);
		murG.setWidth(10);
		murG.setPosition(new Vector2f(0, 0));
		itemsList.add(murG);
		// sol
		Background sol = new Background(new Image("data/rectangle.png") , new String("Sol") );
		sol.setHeight(10);
		sol.setWidth(windowWidth);
		sol.setPosition(new Vector2f(0, windowHeight - 10));
		itemsList.add(sol);
		PlateformeItem plateform1 = new PlateformeItem(new Image("data/rectangle.png") , new String("plateform1"));
		plateform1.setHeight(10);
		plateform1.setWidth(150);
		plateform1.setPosition(new Vector2f(100,150));
		plateform1.setCelerity(new Vector2f((float)0.5,0));
		itemsList.add(plateform1);
		PlateformeItem plateform2 = new PlateformeItem(new Image("data/rectangle.png") , new String("plateform2"));
		plateform2.setHeight(10);
		plateform2.setWidth(150);
		plateform2.setPosition(new Vector2f(200,250));
		plateform2.setCelerity(new Vector2f((float)0.3,0));
		itemsList.add(plateform2);
		PlateformeItem plateform3 = new PlateformeItem(new Image("data/rectangle.png") , new String("plateform3"));
		plateform3.setHeight(10);
		plateform3.setWidth(150);
		plateform3.setPosition(new Vector2f(300,350));
		plateform3.setCelerity(new Vector2f((float)0.4,0));
		itemsList.add(plateform3);
		PlateformeItem plateform4 = new PlateformeItem(new Image("data/rectangle.png") , new String("plateform4"));
		plateform4.setHeight(10);
		plateform4.setWidth(150);
		plateform4.setPosition(new Vector2f(50,450));
		plateform4.setCelerity(new Vector2f((float)0.2,0));
		itemsList.add(plateform4);
		Background toit = new Background(new Image("data/rectangle.png") , new String("toit"));
		toit.setHeight(10);
		toit.setWidth(windowWidth);
		toit.setPosition(new Vector2f(0,0));
		itemsList.add(toit);
		/*
		liste = new ArrayList<ItemsPhy>();
		
		ItemsPhy item1 = new ItemsPhy(new Vector2f(50, 50), new Image("data/rond.png"), true, 1, 0);
		item1.setCelerity( new Vector2f((float) 0, (float) 0) );
		item1.getItemModel().com = new String("bal");
		item1.setGravity(true);
		item1.setHeight(item1.getItemModel().getImage().getHeight());
		item1.setWidth(item1.getItemModel().getImage().getWidth());
		liste.add(item1);
		ItemsPhy item3 = new ItemsPhy(new Vector2f(0,570), new Image("data/rectangle.png"), false, 0, 0 );
		item3.getItemModel().com = new String("Sol");
		item3.setHeight( 30 );
		item3.setWidth( 800 );
		liste.add(item3);
		ItemsPhy item2 = new ItemsPhy(new Vector2f(620,250), new Image("data/rectangle.png"), false, 0, 0 );
		item2.getItemModel().com = new String("mur");
		item2.setHeight(item2.getItemModel().getImage().getHeight()+ 250);
		item2.setWidth(item2.getItemModel().getImage().getWidth() + 50);
		liste.add(item2);
		ItemsPhy murDroit =  new ItemsPhy(new Vector2f(790,0), new Image("data/rectangle.png"), false, 0, 0 );
		murDroit.getItemModel().com = new String("Mur droit");
		murDroit.setHeight( 600 );
		murDroit.setWidth( 10 );
		liste.add(murDroit); 
		ItemsPhy murGauche =  new ItemsPhy(new Vector2f(0,0), new Image("data/rectangle.png"), false, 0, 0 );
		murGauche.getItemModel().com = new String("mur Gauche");
		murGauche.setHeight( 600 );
		murGauche.setWidth( 10 );
		liste.add(murGauche); 
		
		ItemsPhy item4 = new ItemsPhy(new Vector2f(200, 570),new Image("data/rectangle.png") , false, 0, 0);
		item4.setHeight(item4.getItemModel().getImage().getHeight());
		item4.setWidth(item4.getItemModel().getImage().getWidth());
		item4.getPosition().y = item3.getPosition().y - item4.getHeight();
		item4.getItemModel().com = new String("block");
		//liste.add(item4);
		// mont�
		ItemsPhy item5 = new ItemsPhy(new Vector2f(450,570), new Image("data/rectangle.png"), false, 0, 0);
		item5.getItemModel().com = new String("M1");
		item5.setHeight( 3 );
		item5.setWidth( 10 );
		liste.add(item5);
	
		ItemsPhy item6 = new ItemsPhy(new Vector2f(460,568), new Image("data/rectangle.png"), false, 0, 0);
		item6.getItemModel().com = new String("M2");
		item6.setHeight( 3 );
		item6.setWidth( 10 );
		liste.add(item6);
		ItemsPhy item7 = new ItemsPhy(new Vector2f(470,566), new Image("data/rectangle.png"), false, 0, 0);
		item7.getItemModel().com = new String("M3");
		item7.setHeight( 3 );
		item7.setWidth( 10 );
		liste.add(item7);
		ItemsPhy item8 = new ItemsPhy(new Vector2f(480,564), new Image("data/rectangle.png"), false, 0, 0);
		item8.getItemModel().com = new String("M3");
		item8.setHeight( 3 );
		item8.setWidth( 10 );
		liste.add(item8);
		ItemsPhy item9 = new ItemsPhy(new Vector2f(490,562), new Image("data/rectangle.png"), false, 0, 0);
		item9.getItemModel().com = new String("M3");
		item9.setHeight( 3 );
		item9.setWidth( 10 );
		liste.add(item9);
		ItemsPhy item10 = new ItemsPhy(new Vector2f(500,560), new Image("data/rectangle.png"), false, 0, 0);
		item10.getItemModel().com = new String("M3");
		item10.setHeight( 3 );
		item10.setWidth( 10 );
		liste.add(item10);
		ItemsPhy item11 = new ItemsPhy(new Vector2f(510,558), new Image("data/rectangle.png"), false, 0, 0);
		item11.getItemModel().com = new String("M3");
		item11.setHeight( 3 );
		item11.setWidth( 10 );
		liste.add(item11);
		
		ItemsPhy newItem1 = new ItemsPhy(new Vector2f(210,470), new Image("data/rectangle.png"), false, 0, 0);
		newItem1.getItemModel().com = new String("1");
		newItem1.setHeight(newItem1.getItemModel().getImage().getHeight()+ 30);
		newItem1.setWidth(newItem1.getItemModel().getImage().getWidth()- 20);
		liste.add(newItem1);
		ItemsPhy newItem2 = new ItemsPhy(new Vector2f(310,440), new Image("data/rectangle.png"), false, 0, 0);
		newItem2.getItemModel().com = new String("2");
		newItem2.setHeight(newItem2.getItemModel().getImage().getHeight()+ 20);
		newItem2.setWidth(newItem2.getItemModel().getImage().getWidth()- 20);
		liste.add(newItem2);
		ItemsPhy newItem3 = new ItemsPhy(new Vector2f(210,400), new Image("data/rectangle.png"), false, 0, 0);
		newItem3.getItemModel().com = new String("3");
		newItem3.setHeight(newItem3.getItemModel().getImage().getHeight()+ 20);
		newItem3.setWidth(newItem3.getItemModel().getImage().getWidth()- 20);
		liste.add(newItem3);
		ItemsPhy newItem4 = new ItemsPhy(new Vector2f(310,370), new Image("data/rectangle.png"), false, 0, 0);
		newItem4.getItemModel().com = new String("4");
		newItem4.setHeight(newItem4.getItemModel().getImage().getHeight() + 20);
		newItem4.setWidth(newItem4.getItemModel().getImage().getWidth() - 20);
		liste.add(newItem4);
		ItemsPhy newItem5 = new ItemsPhy(new Vector2f(210,330), new Image("data/rectangle.png"), false, 0, 0);
		newItem5.getItemModel().com = new String("5");
		newItem5.setHeight(newItem5.getItemModel().getImage().getHeight() + 20);
		newItem5.setWidth(newItem5.getItemModel().getImage().getWidth()- 20);
		liste.add(newItem5);
		ItemsPhy newItem8 = new ItemsPhy(new Vector2f(310,300), new Image("data/rectangle.png"), false, 0, 0);
		newItem8.getItemModel().com = new String("8");
		newItem8.setHeight(newItem8.getItemModel().getImage().getHeight() + 20);
		newItem8.setWidth(newItem8.getItemModel().getImage().getWidth()- 20);
		liste.add(newItem8);
		ItemsPhy newItem9 = new ItemsPhy(new Vector2f(210,270), new Image("data/rectangle.png"), false, 0, 0);
		newItem9.getItemModel().com = new String("9");
		newItem9.setHeight(newItem9.getItemModel().getImage().getHeight() + 20);
		newItem9.setWidth(newItem9.getItemModel().getImage().getWidth()- 20);
		liste.add(newItem9);
		ItemsPhy newItem10 = new ItemsPhy(new Vector2f(310,240), new Image("data/rectangle.png"), false, 0, 0);
		newItem10.getItemModel().com = new String("10");
		newItem10.setHeight(newItem10.getItemModel().getImage().getHeight() + 20);
		newItem10.setWidth(newItem10.getItemModel().getImage().getWidth()- 20);
		liste.add(newItem10);
		ItemsPhy newItem11 = new ItemsPhy(new Vector2f(210,200), new Image("data/rectangle.png"), false, 0, 0);
		newItem11.getItemModel().com = new String("11");
		newItem11.setHeight(newItem11.getItemModel().getImage().getHeight() + 20);
		newItem11.setWidth(newItem11.getItemModel().getImage().getWidth()- 20);
		liste.add(newItem11);
		ItemsPhy newItem12 = new ItemsPhy(new Vector2f(310,170), new Image("data/rectangle.png"), false, 0, 0);
		newItem12.getItemModel().com = new String("12");
		newItem12.setHeight(newItem12.getItemModel().getImage().getHeight() + 20);
		newItem12.setWidth(newItem12.getItemModel().getImage().getWidth()- 20);
		liste.add(newItem12);
		ItemsPhy newItem6 = new ItemsPhy(new Vector2f(320,150), new Image("data/rectangle.png"), false, 0, 0);
		newItem6.getItemModel().com = new String("plateforme");
		newItem6.setHeight(newItem6.getItemModel().getImage().getHeight());
		newItem6.setWidth(newItem6.getItemModel().getImage().getWidth() + 70);
		liste.add(newItem6);
		ItemsPhy newItem7 = new ItemsPhy(new Vector2f(310,500), new Image("data/rectangle.png"), false, 0, 0);
		newItem7.getItemModel().com = new String("0");
		newItem7.setHeight(newItem7.getItemModel().getImage().getHeight() + 20);
		newItem7.setWidth(newItem7.getItemModel().getImage().getWidth() - 20);
		liste.add(newItem7);
		*/
		/* */
		/*
		int i = 0;
		while (i < 100 ) {
			ItemsPhy newItem = new ItemsPhy(new Vector2f(310,500), new Image("data/rectangle.png"), false, 0, 0);
			newItem.setHeight(newItem.getItemModel().getImage().getHeight());
			newItem.setWidth(newItem.getItemModel().getImage().getWidth());
			liste.add(newItem);
			i ++;
		}
		*/
	}
	
	
}
