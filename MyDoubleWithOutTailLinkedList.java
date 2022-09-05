package nodes;

import org.w3c.dom.Node;
import nodes.DNode;

import java.io.Serializable;
import java.util.Random;

/********************************************************************
 * A linked list program that sorts a
 * Double Linked List Without a Tail using nodes.
 *
 * @author - Gavin Morrow, Major: Computer Science
 *
 * @version - 3/14/22
 ******************************************************************/
public class MyDoubleWithOutTailLinkedList implements Serializable {

    /****************************************************************
     * A private instance variable that sets a node to the top
     * of the linked list
     **************************************************************/
    private DNode top;

    /****************************************************************
     * A Linked List constructor that sets the top node to null
     **************************************************************/
    public MyDoubleWithOutTailLinkedList() {
        top = null;
    }

    // This method has been provided and you are not permitted to
    // modify

    /****************************************************************
     * A method that calculates the size of the list.
     *
     * @return - returns the size of the list.
     *
     * @throws - RuntimeException in the event that total next
     * links do not match previous links
     **************************************************************/

    public int size() {
        if (top == null)
            return 0;

        int total = 0;
        DNode temp = top;
        while (temp != null) {
            total++;
            temp = temp.getNext();
        }

        int totalBack = 0;
        temp = top;
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }

        while (temp != null) {
            totalBack++;
            temp = temp.getPrev();
        }

        if (total != totalBack) {//not matching stops program
            //GUI will not work
            System.out.println("next links " + total +
                    " do not match prev links " + totalBack);
            throw new RuntimeException();
        }

        return total;

    }

    // This method has been provided and you are not permitted to
    // modify

    /****************************************************************
     * A method that clears a random number of list items.
     **************************************************************/

    public void clear() {
        Random rand = new Random(13);
        while (size() > 0) {
            int number = rand.nextInt(size());
            remove(number);
        }
    }

    /****************************************************************
     * A method that adds a rental object to the list ordered by
     * games first, ordered by dueDate, then ordered by consoles
     * second, ordered by dueDate.
     *
     * @param s - represents the specific rental object that is
     * being used in each scenario/case.
     *
     * @return - each instance of game or console.
     **************************************************************/

    public void add(Rental s) {

        //no list
        if (top == null) {
            top = new DNode(s, null, null);
            return;
        }
        //insert Game and have dueBack date before the top node
        if (top.getData() instanceof Game && s instanceof Game
                && top.getData().dueBack.after(s.dueBack)) {
            top = new DNode(s, top, null);
            top.getNext().setPrev(top);
            return;
        }
        //insert Game and have same dueBack date but lower name
        //compare with top node
        if (top.getData() instanceof Game && s instanceof Game
                && top.getData().dueBack.equals(s.dueBack)
                && top.getData().nameOfRenter.
                compareTo(s.nameOfRenter) > 0) {
            top = new DNode(s, top, null);
            top.getNext().setPrev(top);
            return;
        }

        //insert Game when top node is Console
        if (top.getData() instanceof Console && s instanceof Game) {
            top = new DNode(s, top, null);
            top.getNext().setPrev(top);
            return;
        }

        //insert Console have dueBack before the top node (Console)
        if (top.getData() instanceof Console && s instanceof Console
                && top.getData().dueBack.after(s.dueBack)) {
            top = new DNode(s, top, null);
            top.getNext().setPrev(top);
            return;
        }

        //insert Console and have same dueBack date but lower name
        //compare with top node (Console)
        if (top.getData() instanceof Console && s instanceof Console
                && top.getData().dueBack.equals(s.dueBack)
                && top.getData().nameOfRenter
                .compareTo(s.nameOfRenter) > 0) {
            top = new DNode(s, top, null);
            top.getNext().setPrev(top);
            return;
        }

        //case for insert data is Game
        if (s instanceof Game) {
            DNode temp = top;
            //traverse to the last node that have dueBack date before
            //the insert node
            while (temp != null && temp.getNext() != null
                    && temp.getData() instanceof Game
                    && temp.getNext().getData() instanceof Game
                    && temp.getData().dueBack.before(s.dueBack)
                    && temp.getNext().getData().dueBack.
                    before(s.dueBack)) {
                temp = temp.getNext();
            }
            //if next node is not the end of the list
            if (temp.getNext() != null) {
                //if the next node has same dueBack date with
                //insert data
                if (temp.getNext().getData().dueBack.equals
                        (s.dueBack)) {
                    temp = temp.getNext();
                    //case if name of insert data is
                    //before current node
                    if (temp.getData().nameOfRenter.
                            compareTo(s.nameOfRenter) > 0) {
                        DNode insert = new DNode(s, temp,
                                temp.getPrev());
                        temp.setPrev(insert);
                        insert.getPrev().setNext(insert);
                    }
                    //case if name of insert data is not before
                    //current node
                    else {
                        //traverse to last node that has same due
                        //date, have name before insert data
                        while (temp != null && temp.getNext() != null
                                && temp.getData() instanceof Game
                                && temp.getNext().getData()
                                instanceof Game
                                && temp.getData().dueBack.
                                equals(s.dueBack)
                                && temp.getData().nameOfRenter.
                                compareTo(s.nameOfRenter) <= 0
                                && temp.getNext().getData().
                                dueBack.equals(s.dueBack)
                                && temp.getNext().getData().
                                nameOfRenter.
                                compareTo(s.nameOfRenter) <= 0
                        ) {
                            temp = temp.getNext();
                        }
                        //if the next node is null
                        //insert data to the end
                        if (temp.getNext() != null) {
                            DNode insert = new DNode
                                    (s, temp.getNext(), temp);
                            temp.setNext(insert);
                            insert.getNext().setPrev(insert);
                        }
                        //if next node is not null
                        else {
                            temp.setNext(new DNode(s, null, temp));
                            return;
                        }//end case if name of insert data is not
                        //before the current node
                    }//end case have same dueBack date
                }
                //if next node is dueBack data isn't same as
                //insert data
                //later dueBack date compared to insert data
                else {
                    DNode insert = new DNode(s, temp.getNext(), temp);
                    temp.setNext(insert);
                    insert.getNext().setPrev(insert);
                    return;
                }
            }
            //if each end of the list then insert data to end
            else {
                temp.setNext(new DNode(s, null, temp));
                return;
            }
        }//end case insert data is Game
        //case insert data is Console


        //case for insert data is Console
        if (s instanceof Console) {
            DNode temp = top;
            //traverse to the last node that have dueBack date before
            //the insert node
            if (top.getData() instanceof Game) {
                while (temp != null && temp.getNext() != null
                        && temp.getData() instanceof Game
                        && temp.getNext().getData() instanceof Game) {
                    temp = temp.getNext();
                }
                //if next node is not the end of the list
            }
            if (temp.getNext() != null) {
                //if the next node has same dueBack date with
                //insert data
                temp = temp.getNext();
                //case if name of insert data is
                //before current node
                if (temp.getData() instanceof Console && s.dueBack.
                        before(temp.getData().dueBack)) {
                    DNode insert = new DNode(s, temp, temp.getPrev());
                    temp.setPrev(insert);
                    insert.getPrev().setNext(insert);
                    return;
                }
                //if due dates are same, then compare name of renters
                if (temp.getData() instanceof Console &&
                        s.dueBack.equals(temp.getData().dueBack)
                        && s.nameOfRenter.compareTo
                        (temp.getData().nameOfRenter) < 0) {
                    DNode insert = new DNode(s, temp, temp.getPrev());
                    temp.setPrev(insert);//inserts data in prev slot
                    insert.getPrev().setNext(insert);
                    return;//returns sorted data
                }
                while (temp != null && temp.getNext() != null
                        && temp.getData() instanceof Console
                        && temp.getNext().getData() instanceof Console
                        && temp.getData().dueBack.before(s.dueBack)
                        && temp.getNext().getData
                        ().dueBack.before(s.dueBack)) {
                    temp = temp.getNext();
                }
                if (temp.getNext() != null) {
                    if (temp.getNext().getData().
                            dueBack.equals(s.dueBack)) {
                        temp = temp.getNext();
                        if (temp.getData().nameOfRenter.
                                compareTo(s.nameOfRenter) > 0) {
                            DNode insert = new DNode
                                    (s, temp, temp.getPrev());
                            temp.setPrev(insert);//inserts data
                            //in previous node
                            insert.getPrev().setNext(insert);
                        } else {
                            while (temp != null && temp.getNext()
                                    != null
                                    && temp.getData() instanceof
                                    Console &&
                                    temp.getNext().getData()
                                            instanceof Console
                                    && temp.getData().
                                    dueBack.equals(s.dueBack)
                                    //if name of renter and
                                    //date due are the same
                                    && temp.getData().nameOfRenter.
                                    compareTo(s.nameOfRenter) <= 0
                                    && temp.getNext().getData().
                                    dueBack.equals(s.dueBack)
                                    && temp.getNext().getData().
                                    nameOfRenter.compareTo
                                            (s.nameOfRenter) <= 0
                            ) {
                                temp = temp.getNext();
                            }
                            if (temp.getNext() != null) {
                                DNode insert = new DNode
                                        (s, temp.getNext(), temp);
                                temp.setNext(insert);//inserts
                                //rental before initial rental
                                insert.getNext().setPrev(insert);
                            } else {
                                temp.setNext(new DNode(s, null, temp));
                                return;
                            }
                        }
                    } else {
                        DNode insert = new DNode(s, temp.getNext(),
                                temp);
                        temp.setNext(insert);
                        insert.getNext().setPrev(insert);
                        return;
                    }
                } else {
                    temp.setNext(new DNode(s, null, temp));
                    return;
                }

            } else {
                temp.setNext(new DNode(s, null, temp));
                return;
            }
        }

    }

    /***************************************************************
     * A method that removes a rental object from the list.
     *
     * @param index - represents the index of the list
     *
     * @return - the rental unit being removed.
     *
     * @throws - IllegalArgumentException in the event
     * the size of the index is less than 0.
     *************************************************************/

    public Rental remove(int index) {
        //case 1 index isn't valid
        if (index < 0) {
            throw new IllegalArgumentException();
        }
            //case 0 no list
        if (top == null)
            return null;

        if (index >= size()) {
            return null;
        }
        if (index == 0) {
            Rental data = top.getData();
            top = top.getNext();//get top data
            if (top != null) {
                //if top has data, set it to null
                top.setPrev(null);
            }
            return data;
        }
        DNode temp = top;
        while (index > 0) {
            temp = temp.getNext();
            index--;
        }
        if (temp.getNext() == null) {
            Rental data = temp.getData();
            temp.getPrev().setNext(null);
            return data;//returns what was deleted
        } else {
            Rental data = temp.getData();
            temp.getPrev().setNext(temp.getNext());
            temp.getNext().setPrev(temp.getPrev());
            return data;//returns deleted data
        }


    }

    /***************************************************************
     * A method that gets a rental object from the list.
     *
     * @param index - represents the index of the list
     *
     * @return - the unit that is being rented.
     *
     * @throws - IllegalArgumentException in the event the
     * size of the index is less than 0.
     ************************************************************/

    public Rental get(int index) {
        if (index < 0) {//checks size of index
            throw new IllegalArgumentException();
        }
        if (top == null)// case 0
            return null;
        if (index >= size()) {
            return null;
        }
        DNode temp = top;
        while (index > 0) {
            temp = temp.getNext();
            index--;
        }
        return temp.getData();

    }


    /**************************************************************
     * A method that displays the list of all the rental units,
     * games, consoles, the renters name, etc.
     ************************************************************/

    public void display() {
        DNode temp = top;
        while (temp != null) {//if temporary node
            //has data present print it
            System.out.println(temp.getData());
            temp = temp.getNext();
        }
    }

    /***************************************************************
     * A method returns a string that represents the list of rentals
     * in the format "LL {top = [top], size = [size()]}
     *
     *  @return - a formatted string of all the rental units.
     *************************************************************/

    public String toString() {
        return "LL {" +
                "top=" + top +//prints a formatted string
                ", size=" + size() +
                '}';
    }

}