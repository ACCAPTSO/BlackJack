import java.lang.Math;
import java.util.HashSet;
import java.util.Vector;
import java.util.Iterator;

// can this be done with a HashMap? can you change the keys of members of a HashMap?

class Deck {

  Vector<Card> deck = new Vector<Card>(52);

  Deck() {
    restore();
  }

  void restore() {
    for( int i = 1; i < 14; i++  ) {
      deck.add( new Card( "hearts", i ) ); 
      // panel in Card not displayable
      //deck.lastElement().buildImage();
      deck.add( new Card( "diamonds", i ) ); 
      deck.add( new Card( "spades", i ) );
      deck.add( new Card( "clubs", i ) ); 
    }
  }

  void shuffle() {
    HashSet <Integer> hashset = new HashSet<Integer>();
    int sval;

    for( int i = 0; i < 52; i++ ) {
      while( !hashset.add( sval = (int)( Integer.MAX_VALUE*Math.random() ) ) );
      deck.elementAt(i).setSortOrder( sval );
    }
    insSort();
  }

  void insSort() {
    Vector<Card> tmp = new Vector<Card>(52);

    tmp.add( deck.firstElement() ); 
    for( int i = 1; i < 52; i++ ) {
      int s = 0;
      while( s < tmp.size() &&
             tmp.elementAt(s).getSortOrder() < deck.elementAt(i).getSortOrder() )
        s++;
      tmp.insertElementAt( deck.elementAt(i), s-- );
    }
    deck = tmp; 
  }

  void display() {
    Iterator<Card> deckitr = deck.iterator();
    while( deckitr.hasNext() ) {
      deckitr.next().showCard();
    }
  }

  int size() {
    return deck.size();
  }

  void draw( Hand player ) {
    if( !deck.isEmpty() ) {
      Card drawCard = deck.firstElement();
      deck.remove(0);
      player.setCard( drawCard );
    }
  } 

  int getSize() {
    return deck.size();
  }
}
