package ua.edu.sumdu.ta.Nikolaj_Yekymov.pr6;

import java.io.*;
import java.util.*;

public class LinkedTaskList extends AbstractTaskList implements Cloneable, Serializable {
	
	private LinkedTaskListNode head;
	private LinkedTaskListNode tail;
	
	public LinkedTaskList() {
		
		listsNumber++;
	}
	
	public Iterator<Task> iterator() {
		return new LinkedListIterator();
	}
	
	public void add(Task task) {
		if(task == null)
			throw new IllegalArgumentException("The argument of \"add\" method cannot be null");
		LinkedTaskListNode node = new LinkedTaskListNode(task);
		if (head == null) {
			head = node;
		} else {
			tail.next = node;
			node.previous = tail;
		}
		tail = node;
		String s = BEGINNING_OF_LIST_TASK_TITLE + node.value.getTitle();
		if(!node.value.getTitle().contains(BEGINNING_OF_LIST_TASK_TITLE))
			node.value.setTitle(s);
		size++;
	}
	
	public void remove(Task task) {
		if(task == null)
			throw new IllegalArgumentException("The argument of \"remove\" method cannot be null");
		LinkedTaskListNode prev = null;
		LinkedTaskListNode current = head;
		while (current != null) {
			if (current.value.equals(task)) {
				if (prev != null) {
					prev.next = current.next;
					if (current.next == null) {
						tail = prev;
					} else {
						current.next.previous = prev;
					}
					size--;
				} else {
					if (size != 0) {
						head = head.next;
						size--;
					}
					if (size == 0) {
						tail = null;
					} else {
						head.previous = null;
					}
				}
			}
			prev = current;
			current = current.next;
		}
	}
	
	public Task[] incoming(int from, int to) {
		Task[] newArr = new Task[size() - 1];
		LinkedTaskListNode current = tail;
		while (current != null) {
			if(current.value.isActive()) {
				if(current.value.getStartTime() > from && current.value.getStartTime() <= to) {
					addTaskToIncomingList(newArr,current.value);
				} else if(current.value.isRepeated()) {
					int res = current.value.getStartTime() + current.value.getRepeatInterval();
					if(res > from && res <= to) {
						addTaskToIncomingList(newArr,current.value);
					} else {
						while(res < current.value.getEndTime()) {
							res += current.value.getRepeatInterval();
							if ((current.value.getEndTime() - res) < 0) {
								break;
							} else if(res > from && res <= to) {
								addTaskToIncomingList(newArr,current.value);
								break;
							}
						}
					}	
				}
			}
			current = current.previous;
		}
		newArr = incomingListCloning(newArr);
		return newArr;
	}
	
	private void addTaskToIncomingList(Task[] arr, Task t) {
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] == null) {
				arr[i] = t;
				break;
			}
		}
	}
	
	private Task[] incomingListCloning(Task[] arr) {
		int g = 0;
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] == null) {
				g++;
			}
		}
		Task[] newList = new Task[arr.length - g];
		for(int j = 0; j < newList.length; j++) {
			Task cloned = arr[j].clone();
			newList[j] = cloned;
		}		
		return newList;
	}
	
	private class LinkedListIterator implements Iterator<Task> {
		
		LinkedTaskListNode current = head;
		
		int index = 0;
		
		@Override
		public boolean hasNext(){
			if(size == 0) {
				System.out.println("This List is empty");
			}
			boolean result = (index < size)?true:false;
			return result;
		}
		
		@Override
		public Task next(){
			if(index == 0) {
				index++;
				return current.value;
			}
			if(current != null) {	 
				current = current.next;
			}
			index++;
			return current.value;
		}
	}
}