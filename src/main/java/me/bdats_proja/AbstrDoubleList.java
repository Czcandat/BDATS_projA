package me.bdats_proja;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class AbstrDoubleList<T> implements Iterable<T>
{
    private Node<T> active;
    private Node<T> first;


    public AbstrDoubleList()
    {
        this.active = null;
        this.first = null;
    }


    public void zrus()
    {
        while(!isEmpty())
        {
            odeberPrvni();
        }
    }


    public boolean isEmpty()
    {
        return first == null;
    }


    // Insert item
    public void vlozPrvni(T data)
    {
        if (data == null) return;

        if(isEmpty())
        {
            vlozNaslednika(data);
            return;
        }

        Node<T> tempA = active;
        Node<T> tempF = first;
        active = first.prev;
        vlozNaslednika(data);
        active = tempA;
        first = tempF.prev;
    }


    public void vlozPosledni(T data)
    {
        if (data == null) return;

        if(isEmpty())
        {
            vlozNaslednika(data);
            return;
        }

        Node<T> tempA = active;
        active = first;
        vlozPredchudce(data);
        active = tempA;
    }


    public void vlozNaslednika(T data)
    {
        if (data == null) return;

        Node<T> newNode = new Node<>(data);

        if (isEmpty())
        {
            first = newNode;
            first.next = first;
            first.prev = first;
            active = first;
            return;
        }

        newNode.prev = active;
        newNode.next = active.next;
        newNode.next.prev = newNode;
        active.next = newNode;
    }


    public void vlozPredchudce(T data)
    {
        if (data == null) return;

        if (isEmpty())
        {
            vlozNaslednika(data);
            return;
        }

        Node<T> temp = active;
        active = active.prev;
        vlozNaslednika(data);
        active = temp;
    }


    // Access item
    public Node<T> zpristupniAktualni()
    {
        if (active == null) return null;
        return active;
    }


    public Node<T> zpristupniPrvni()
    {
        if (active == null) return null;

        active = first;
        return active;
    }


    public Node<T> zpristupniPosledni()
    {
        if (active == null) return null;

        active = first.prev;
        return active;

    }


    public Node<T> zpristupniNaslednika()
    {
        if (active == null) return null;

        active = active.next;
        return active;

    }


    public Node<T> zpristupniPredchudce()
    {
        if (active == null) return null;
        active = active.prev;
        return active;
    }


    // Remove item
    public Node<T> odeberAktualni()
    {
        // do first logic
        if (isEmpty()) return null;
        if (active == active.next)
        {
            Node<T> tempA = active;
            active = null;
            first = null;
            tempA.next = null;
            tempA.prev = null;
            return tempA;
        }
        else if (active == first)
        {
            first = first.next;
        }
        Node<T> tempA = active;
        active.prev.next = active.next;
        active.next.prev = active.prev;
        active = first;
        return tempA;
    }

    public Node<T> odeberPrvni()
    {
        if (isEmpty()) return null;
        Node<T> tempA = active;
        if (active == first) {tempA = first.next;}
        else {active = first;}
        first = first.next;
        Node<T> tempRet = odeberAktualni();
        active = tempA;
        return tempRet;
    }

    public Node<T> odeberPosledni()
    {
        if (isEmpty()) return null;
        Node<T> tempA = active;
        active = first.prev;
        Node<T> tempRet = odeberAktualni();
        active = tempA;
        return tempRet;
    }

    public Node<T> odeberNaslednika()
    {
        if (isEmpty()) return null;
        Node<T> tempA = active;
        active = active.next;
        Node<T> tempRet = odeberAktualni();
        active = tempA;
        return tempRet;
    }

    public Node<T> odeberPredchudce()
    {
        if (isEmpty()) return null;
        Node<T> tempA = active;
        active = active.prev;
        Node<T> tempRet = odeberAktualni();
        active = tempA;
        return tempRet;
    }

    @Override
    public Iterator<T> iterator()
    {
        return new DoubleListIterator();
    }

    private class DoubleListIterator implements Iterator<T>
    {
        private Node<T> current;
        private boolean atStart;

        public DoubleListIterator()
        {
            this.current = first;
            this.atStart = true;
        }

        @Override
        public boolean hasNext()
        {
            if (isEmpty())
            {
                return false;
            }
            return atStart || current != first;
        }

        @Override
        public T next()
        {
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }

            T data = current.data;
            current = current.next;
            atStart = false;
            return data;
        }
    }

    // Node class
    public static class Node<T>
    {
        T data;
        Node<T> next;
        Node<T> prev;

        Node(T data)
        {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }
}
