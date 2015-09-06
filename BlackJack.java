import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Iterator;
import java.util.Vector;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class BlackJack extends JFrame implements Runnable, MouseListener {
  Color bloodRed = new Color( 245, 0, 40 );
  Color teal = new Color( 116, 156, 147 );
  Color dkTeal = new Color( 26, 96, 87 );
  Color gold = new Color( 252, 252, 163 );
  static Color dkGreen = new Color( 23, 77, 23 );
  public Image newImg;
  private Graphics gc;
  private int nCards = 2;
  Vector<Hand> players = new Vector<Hand>();
  private int nPlayers = 0;
  private int xPos;
  private int yPos;
  Deck cardDeck = new Deck();
  private Thread cycle;
  private static int xWinDim = 900;
  private static int yWinDim = 340;
  int xst = 9; // viewing area starts at x = 9
  int yst = 36; // viewing area starts at y = 36
 
  // spacing of cards
  private int cardOffs = 30;

  // size of cards
  private int xdim = 70;
  private int ydim = 100;

  // location of the first cards dealt
  private int xl[] = { xst +  20, xst + 300, xst + 580 };
  private int xr[] = { xst + 220, xst + 500, xst + 780 };
  private int yu[] = { yst +  80, yst +  80, yst +  80 };
  private int yl[] = { yu[0] + ydim, yu[1] + ydim, yu[2] + ydim };

  // location of the done hits button
  private int dhl = xst + 100;
  private int dhr = xst + 220;
  private int dhu = yst + 215;
  private int dhb = yst + 275;

  // location of the play again button
  private int pal = xst + 620;
  private int par = xst + 740;
  private int pau = dhu;
  private int pab = dhb;
  private boolean doneHits = false;
  private boolean playAgain = false;
  private boolean dispPlayAgain = true;
  private final int NUMDEN = 10;
  private int cardsSeen[];
  private int cardsThereAre[];
  private boolean ok2paint = false;

  BlackJack( String s ) {
    //this.setBackground( dkGreen );
    super( s );
    players.add( new Hand( "Curly" ) );
    players.add( new Hand( "Mo" ) );
    players.add( new Hand( "Larry" ) );
    nPlayers = players.size();
    System.out.println("before shuffling: ");
    cardDeck.display();
    cardDeck.shuffle();
    System.out.println();
    System.out.println("after shuffling: ");
    cardDeck.display();
    cardsSeen = new int[NUMDEN];
    for( int i = 0; i < NUMDEN; i++ ) {
      cardsSeen[i] = 0;
    }
    cardsThereAre = new int[NUMDEN];
    cardsThereAre[0] = 4; // Aces
    cardsThereAre[1] = 4;
    cardsThereAre[2] = 4;
    cardsThereAre[3] = 4;
    cardsThereAre[4] = 4;
    cardsThereAre[5] = 4;
    cardsThereAre[6] = 4;
    cardsThereAre[7] = 4;
    cardsThereAre[8] = 4;
    cardsThereAre[9] = 16; // 10s, Jacks, Queens and Kings
    if( cycle == null ) {
      cycle = new Thread( this ); // this starts the RUN method
      cycle.start();
    }
    ok2paint = true;
  }

  void drawHands( Graphics g, boolean up ) {
    int xoffs = xst;
    int yoffs = yst;
    int stackHeight = cardDeck.getSize();
    //g.setColor( Color.orange );
    //g.fillRect( xst, yst, 10, 10 );
    //g.setColor( Color.white );
    //g.fillRect( xst+1, yst+1, 8, 8 );
    //xst = 881; // viewing area ends at x = 891
    //yst = 321; // viewing area ends at y = 331
    //g.setColor( Color.orange );
    //g.fillRect( xst, yst, 10, 10 );
    //g.setColor( Color.white );
    //g.fillRect( xst+1, yst+1, 8, 8 );
    xoffs = xl[0] + 10;
    for( int i = 0, j = yst + 40; i < stackHeight; i += 5, j -= 2 ) {
      g.setColor( dkTeal );
      g.drawLine( xoffs, j, xoffs+ydim, j );
      g.setColor( teal );
      g.drawLine( xoffs, j-1, xoffs+ydim, j-1 );
    }
    int cardsDealt = 0;
    for( int i = 0; i < nPlayers; i++ ) {
      Hand whatPlayer = players.elementAt(i);
      cardsDealt += whatPlayer.getSize();
    }
    xoffs = xl[2] + 10;
    for( int i = 0, j = yst + 40; i < 52-stackHeight - cardsDealt; 
         i += 5, j -= 2 ) {
      g.setColor( dkTeal );
      g.drawLine( xoffs, j, xoffs+ydim, j );
      g.setColor( teal );
      g.drawLine( xoffs, j-1, xoffs+ydim, j-1 );
    }
    for( int i = 0; i < nPlayers; i++ ) {
      xoffs = xl[i];
      yoffs = yu[i];
      Hand whatPlayer = players.elementAt(i);
      Vector<Card> vec = whatPlayer.getCards();
      String suit;
      String denomination;
      g.setColor( Color.white );
      g.drawString( whatPlayer.getName() + " " + whatPlayer.getRoundsWon(),
                    xoffs+cardOffs+10, yoffs - 15 );        
      Iterator<Card> itr = vec.iterator();
      while( itr.hasNext() ) {
        //g.drawImage( itr.next().getImg(), xoffs, 50, this );
        Card nextCard = itr.next();
        g.setColor( dkTeal );
        g.fillRoundRect( xoffs, yoffs, xdim, ydim, 8, 8 );
        g.setColor( teal );
        g.fillRoundRect( xoffs+2, yoffs+2, xdim-2, ydim-2, 8, 8 );
        if( i == 0 || up ) {
          g.setColor( Color.white );
          g.fillRoundRect( xoffs+2, yoffs+2, xdim-4, ydim-4, 8, 8 );
          denomination = nextCard.getDen();
          suit = nextCard.getSuit(); 
          g.setColor( Color.black );
          g.drawString( denomination, xoffs+7, yoffs+16 );        
          switch( suit ) {
            case "hearts":
              g.setColor( bloodRed );
              int xptt[] = { xoffs+20, xoffs+36, xoffs+52, xoffs+36 };
              int yptt[] = { yoffs+53, yoffs+47, yoffs+53, yoffs+69 };
              g.fillPolygon( xptt, yptt, 4 );
              g.fillOval( xoffs+15, yoffs+35, 22, 20 );
              g.fillOval( xoffs+34, yoffs+35, 22, 20 );
              break;
            case "diamonds":
              g.setColor( bloodRed );
              int xpts[] = { xoffs+21, xoffs+36, xoffs+51, xoffs+36 };
              int ypts[] = {  yoffs+50,  yoffs+25,  yoffs+50, yoffs+75 };
              g.fillPolygon( xpts, ypts, 4 );
              break;
            case "spades":
              g.setColor( Color.black );
              g.fillOval( xoffs+14, yoffs+40, 25, 25 );
              g.fillOval( xoffs+33, yoffs+40, 25, 25 );
              g.fillRect( xoffs+33, yoffs+50, 6, 23 );
              int xptu[] = { xoffs+20, xoffs+36, xoffs+53, xoffs+36 };
              int yptu[] = { yoffs+42, yoffs+26, yoffs+42,  yoffs+52 };
              g.fillPolygon( xptu, yptu, 4 );
              break;
            case "clubs":
              g.setColor( Color.black );
              g.fillOval( xoffs+16, yoffs+42, 21, 21 );
              g.fillOval( xoffs+34, yoffs+42, 21, 21 );
              g.fillOval( xoffs+25, yoffs+26, 21, 21 );
              g.fillRect( xoffs+34, yoffs+50,  4, 23 );
              break;
            default:
              g.setColor( Color.pink );
              g.drawString( "Honey, I'm home", 30, 70 );
          }
        }
        xoffs += cardOffs;
      }
    }
    if( !doneHits ) { 
      g.setColor( gold );
      g.fillRoundRect( dhl, dhu, dhr-dhl, dhb-dhu, 15, 15  );
      g.setColor( dkTeal );
      g.fillRoundRect( dhl+2, dhu+2, dhr-dhl-4, dhb-dhu-4, 15, 15 );
      g.setColor( gold );
      g.drawString( "No More Hits", dhl+25, dhu+35 );
    }
  }

  void drawWinner( Graphics g) {
    int xoffs = 220;
    int score[];
    score = new int[nPlayers];
    int maxScore = 0;
    int idxMax = -1;
    for( int j = 0; j < nPlayers; j++) {
      Hand whatPlayer = players.elementAt(j);
      g.setColor( dkGreen );
      g.fillRect( xl[j]+50, yu[j]-30, 150, 20 );
      score[j] = whatPlayer.getScore();
      if( score[j] > maxScore && score[j] <= 21 ) {
        maxScore = score[j];
        idxMax = j;
      }
    }
    String winner = "nobody";
    int numWinners = 0;
    for( int j = 0; j < nPlayers; j++) {
      if( maxScore == score[j]) {
        if( winner.compareTo( "nobody" ) == 0 ) {
          winner = players.elementAt(j).getName() + " ";
          numWinners = 1;
        } else {
          winner = 
            "tie between " + winner + " and " + players.elementAt(j).getName();
          numWinners += 1;
        }
      }
    }
    winner = "Winner is " + winner;
    g.setColor( Color.white );
    xoffs = xl[1] + 
      ( xdim - cardOffs + cardOffs*players.elementAt(1).getSize() -
        g.getFontMetrics().stringWidth( winner ) )/2;
    g.drawString( winner, xoffs, yl[1] + 60  );        
    if( idxMax >= 0 ) {
      for( int j = 0; j < nPlayers; j++ ) {
        Hand whatPlayer = players.elementAt(j);
        if( whatPlayer.getScore() == maxScore ) {
          int ovalWidth = xdim + 7 + cardOffs*( whatPlayer.getSize() );
          g.drawOval( xl[j]-20, yu[j] - 30, ovalWidth, 160 );
          if( numWinners == 1 ) {
            whatPlayer.incRoundsWon();
          }
        }
      }
    }
  }

  void drawPlayAgain( Graphics g ) {
    g.setColor( gold );
    g.fillRoundRect( pal, pau, par-pal, pab-pau, 15, 15  );
    g.setColor( dkTeal );
    g.fillRoundRect( pal+2, pau+2, par-pal-4, pab-pau-4, 15, 15 );
    g.setColor( gold );
    g.drawString( "Deal Again", pal+25, pau+35 );
  }

  public void init() {
    addMouseListener( this );
  }

  public void update( Graphics g ) { paint( g ); }

  public void paint( Graphics g ) {
    if( ok2paint ) {
      boolean cardsDown = false;
      boolean cardsUp = true;
      newImg = createImage( xWinDim, yWinDim );
      gc = newImg.getGraphics();
   
      drawHands( gc, cardsDown );
      if( doneHits ) {
        drawWinner( gc );
        drawHands( gc, cardsUp );
        if( dispPlayAgain ) {
          drawPlayAgain( gc );
        }
      }
      g.drawImage( newImg, 0, 0, this );
    }
  }

  int probBust( Hand whatHand) {
    int handtotal = whatHand.getScore();
    int bustNumber = 20 - handtotal;
    int cardsGreater = 0;
    int totCards = 0;
    int cardsIhave[];
    cardsIhave = new int[NUMDEN];
    for( int i = 0; i < NUMDEN; i++ ) {
      cardsIhave[i] = 0;
    }
    Iterator<Card>  itr = whatHand.getCards().iterator();
    while( itr.hasNext() ) {
      Card whatCard = itr.next();
      int i = whatCard.getValue() - 1;
      System.out.println( whatHand.getName() + " has " + whatCard.getDen() + " of " + whatCard.getSuit() + " worth " + whatCard.getValue() + " points." );
      cardsIhave[i] += 1;
    }
    for( int i = 0; i < NUMDEN; i++ ) {
      totCards += cardsThereAre[i] - cardsSeen[i] - cardsIhave[i];
      int junk = i+1;
      System.out.println("i+1 = " + junk + " cardsSeen = " + cardsSeen[i] + " cardsIhave = " + cardsIhave[i] );
      if( i > bustNumber ) {
        cardsGreater += cardsThereAre[i] - cardsSeen[i] - cardsIhave[i];
      } 
    }
    int prob = 100*cardsGreater/totCards;
    System.out.println("Prob bust = " + prob );
    return prob;
  }

  int hitOtherplayers( int allDone ) {
    for( int j = 1; j < nPlayers; j++ ) {
      Hand whatPlayer = players.elementAt(j);
      boolean notDone = whatPlayer.getScore() < 12 |
                        probBust( whatPlayer ) < 45;
      if( notDone ) {
        cardDeck.draw( whatPlayer );
        doneHits |= cardDeck.getSize() == 0;
      } else {
        // clear the bits when they're done
        int mask = ~( 1 << j ); 
        System.out.println("Alldone = " + allDone );
        allDone &= mask; 
      }
    }
    return allDone;
  }

  void setValues(int x, int y ) {
    xPos = x;
    yPos = y;
    System.out.println("XPOS = " + xPos + ", YPOS = " + yPos );

    // check if Curly's deck was hit
    if( xl[0] < xPos && xPos < xr[0] && yu[0] < yPos && yPos < yl[0] ) {
      Hand whatPlayer = players.elementAt( 0 );
      cardDeck.draw( whatPlayer );
      doneHits = whatPlayer.getScore() >= 21 || cardDeck.getSize() == 0;
      // hit other players
      hitOtherplayers( 0 );
    }

    // check if "No More Hits" was clicked
    if( dhl < xPos && xPos < dhr && dhu < yPos && yPos < dhb ) {
      doneHits = true;

      int allDone = 0x6; // assume 3 players, one bit set for each other player

      // other hands continute to take hits
      while( allDone !=  0 && cardDeck.getSize() != 0 ) {
        // hit other players
        allDone = hitOtherplayers( allDone );
      }
    }

    // check if "Deal again" was clicked
    if( pal < xPos && xPos < par && pau < yPos && yPos < pab &&
        dispPlayAgain ) {
      playAgain = true;
    }
  }

  // MouseListener event handlers
  public void mouseClicked( MouseEvent e ) {
  }
  public void mousePressed( MouseEvent e ) {
  }
  public void mouseReleased( MouseEvent e ) {
    setValues( e.getX(), e.getY() );
  }
  public void mouseEntered( MouseEvent e ) {
  }
  public void mouseExited( MouseEvent e ) {
  }

  public void run() {
    System.out.println("Thread running");
    while( true ) {
      while( cardDeck.size() > nPlayers*nCards ) {
        // deal cards
        for( int i = 0; i < nCards; i++ ) {
          for( int j = 0; j < nPlayers; j++) {
            cardDeck.draw( players.elementAt(j) );
          }
        }
    
/*
        for( int j = 0; j < nPlayers; j++ ) {
          System.out.println();
          System.out.println( players.elementAt(j).getName() + "'s hand: ");
          players.elementAt(j).display();
        }
*/
        while( !doneHits ) {
          repaint();
          try {
            Thread.sleep( 5 );
          } catch (InterruptedException e ) {
            System.err.println( e.toString() );
          }
        }
        repaint();
        playAgain = false;
        while( !playAgain ) {
          try {
            Thread.sleep( 5 );
          } catch (InterruptedException e ) {
            System.err.println( e.toString() );
          }
        }

        // discard cards
        for( int j = 0; j < nPlayers; j++) {
          Hand whatPlayer = players.elementAt(j);
          Iterator<Card> itr = whatPlayer.getCards().iterator(); 
          while( itr.hasNext() ) {
            Card nextCard = itr.next();
            int value = nextCard.getValue();
            cardsSeen[value-1]++;
          }
          whatPlayer.discardAll();
        }
        doneHits = false;  
      }
      cardDeck.restore();
      cardDeck.shuffle();
      for( int i = 0; i < NUMDEN; i++ ) {
        cardsSeen[i] = 0;
      }
    }
  }

  public static void main( String args[] ) {
    BlackJack bj = new BlackJack("BlackJack");
    bj.setBackground( dkGreen );
    bj.setSize( xWinDim, yWinDim );
    bj.setVisible( true );
    bj.init();
  }
}
