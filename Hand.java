import java.lang.Math;
import java.util.HashSet;
import java.util.Vector;
import java.util.Iterator;

// can this be done with a HashMap? can you change the keys of members of a HashMap?

class Hand {

  String playerName;
  Vector<Card> hand = new Vector<Card>(52);
  int score = 0;
  int roundsWon = 0;

  Hand( String name ) {
    this.playerName = name;
  }

  void setCard( Card newCard ) {
    hand.add( newCard );
    score += newCard.getValue();
  }

  void shuffle() {
    HashSet <Integer> hashset = new HashSet<Integer>();
    int sval;

    for( int i = 0; i < 52; i++ ) {
      while( !hashset.add( sval = (int)( Integer.MAX_VALUE*Math.random() ) ) );
      hand.elementAt(i).setSortOrder( sval );
    }
    insSort();
  }

  void insSort() {
    int sz = hand.size();
    Vector<Card> tmp = new Vector<Card>(sz);

    tmp.add( hand.firstElement() ); 
    for( int i = 1; i < sz; i++ ) {
      int s = 0;
      while( s < tmp.size() && 
        tmp.elementAt(s).getSortOrder() < hand.elementAt(i).getSortOrder() )
        s++;
      tmp.insertElementAt( hand.elementAt(i), s-- );
    }
    hand = tmp; 
  }

  void display() {
    Iterator<Card> handitr = hand.iterator();
    while( handitr.hasNext() ) {
      handitr.next().showCard();

    }
  }

  Vector<Card> getCards() {
    Vector<Card> v = new Vector<Card>();
    Iterator<Card> handitr = hand.iterator();
    while( handitr.hasNext()  ) {
      v.add( handitr.next() );
    }
    return v;
  }

  String getName() {
    return playerName;
  }
  
  int getScore() {
    return score;  
  }

  int getSize() {
    return hand.size();
  }

  void discardAll() {
    hand.clear();
    score = 0;
  }

  int getRoundsWon() {
    return roundsWon;
  }

  void incRoundsWon() {
    roundsWon += 1;
  }
}
