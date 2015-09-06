import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Panel;

class Card{
  String suit;
  String denomination;
  int value;
  int sortorder;
  Color color;
  boolean faceup;
  int xdim = 70;
  int ydim = 100;
  Image img;
  Graphics g;
  Panel panel;
  
  Card( String s, int d ) {
    this.suit = s;
    this.sortorder = d;
    this.faceup = true;
    switch( suit ) {
      case "hearts":
        this.color = Color.red;
        break;
      case "diamonds":
        this.color = Color.red;
        break;
      case "spades":
        this.color = Color.black;
        break;
      case "clubs":
        this.color = Color.black;
        break;
      default:
        this.color = Color.green;    
    }

    switch( d ) {
      case 1: 
        this.denomination = "A";
        this.value = d;
        break;
      case 2: 
        this.denomination = "2";
        this.value = d;
        break;
      case 3: 
        this.denomination = "3";
        this.value = d;
        break;
      case 4: 
        this.denomination = "4";
        this.value = d;
        break;
      case 5: 
        this.denomination = "5";
        this.value = d;
        break;
      case 6: 
        this.denomination = "6";
        this.value = d;
        break;
      case 7: 
        this.denomination = "7";
        this.value = d;
        break;
      case 8: 
        this.denomination = "8";
        this.value = d;
        break;
      case 9: 
        this.denomination = "9";
        this.value = d;
        break;
      case 10: 
        this.denomination = "10";
        this.value = d;
        break;
      case 11: 
        this.denomination = "J";
        this.value = 10;
        break;
      case 12: 
        this.denomination = "Q";
        this.value = 10;
        break;
      case 13: 
        this.denomination = "K";
        this.value = 10;
        break;
      default: 
        this.denomination = "J";
        this.value = 0;
    }
    //buildImage();
  }

  void buildImage() {
    panel = new Panel();
    panel.setVisible( true );
    if( panel.isDisplayable() ) {
      System.out.println("panel is displayable"); 
    } else {
      System.out.println("panel is not displayable"); 
    }

    img = panel.createImage( xdim, ydim ); 
    if( img == null ) {
      System.out.println("img is null");
    }
    g = img.getGraphics();
    g.setColor( Color.white );
    g.fillRect( 0, 0, xdim, ydim );
    g.setColor( Color.black );
    g.drawString( denomination, 10, 10 );        
    g.setColor( color );
    switch( suit ) {
      case "hearts":
        int xpts[] = { 100, 115, 130, 115 };
        int ypts[] = {  77,  52,  77, 102 };
        g.fillPolygon( xpts, ypts, 4 );
        g.fillOval( 32, 110, 20, 20 );
        g.fillOval( 52, 110, 20, 20 );
        break;
      case "diamonds":
        int xptt[] = { 36, 52, 68, 52 };
        int yptt[] = { 168, 160, 168, 184 };
        g.fillPolygon( xptt, yptt, 4 );
      case "spades":
      case "clubs":
        g.setColor( Color.black );
        g.fillOval( 32, 110, 25, 25 );
        g.fillOval( 49, 110, 25, 25 );
        g.fillOval( 41,  95, 25, 25 );
        g.fillRect( 50, 120, 6, 25 );
      default:
    }
  }

  public String getDen() {
    return denomination;
  }

  public String getSuit() {
    return suit;
  }

  public int getValue() {
    return value;
  }

  public void showCard() {
    System.out.println( this.denomination + " of " + this.suit );
  }

  public void setSortOrder( int o ) {
    this.sortorder = o;
  }

  public int getSortOrder() {
    return sortorder;
  }

  public Image getImg() {
    return img;
  }
}
