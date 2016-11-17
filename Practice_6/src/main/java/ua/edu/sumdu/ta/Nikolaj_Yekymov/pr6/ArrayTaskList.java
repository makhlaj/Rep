package ua.edu.sumdu.ta.Nikolaj_Yekymov.pr6;

import java.io.*;
import java.util.*;

public class ArrayTaskList extends AbstractTaskList implements Cloneable, Serializable {
	
	protected static final int TASK_LIST_EXPANSION = 3;
	protected static final int LIST_SIZE = 10;
	public Task[] taskList = new Task[LIST_SIZE];
		
	public ArrayTaskList() {
		
		listsNumber++;
	}
	
	public Iterator<Task> iterator() {
		return new ArrayListIterator();
	}
	
	public int getTaskListExpansion() {
		return TASK_LIST_EXPANSION;
	}
	
	public void add(Task task) {
		if(task == null)
			throw new IllegalArgumentException("The argument of \"add\" method cannot be null");
		for(int i = 0; i < taskList.length; i++) {
			if(taskList[i] == null) {
				taskList[i] = task;
				String s = BEGINNING_OF_LIST_TASK_TITLE + taskList[i].getTitle();
				if(!taskList[i].getTitle().contains(BEGINNING_OF_LIST_TASK_TITLE))
					taskList[i].setTitle(s);
				break;
			}
			else if((taskList.length - i) == 1 && taskList[i] != null) {
				taskListCloningAndExpansion();
			}
		}
		size++;
	}
	
	public void remove(Task task) {
		if(task == null)
			throw new IllegalArgumentException("The argument of \"remove\" method cannot be null");
		for(int i = 0; i < taskList.length; i++) {
			if(taskList[i] != null) {
				if(taskList[i].equals(task)) {
					int a = this.size();
					taskList[i] = null;
					for(int j = 0; j < a; j++) {
						if(taskList[j] == null) {
							taskList[j] = taskList[j+1];
							taskList[j+1] = null;	
						}
					}
					size--;
					break;
				}
			}
		}
	}
	
	public Task[] incoming(int from, int to) {
		Task[] newArr = new Task[getNeededLengthOfIncomingList(from,to)];
			for(Task t : taskList) {
				if(t != null && t.isActive()) {
					if(t.getStartTime() > from && t.getStartTime() <= to) {
						addTaskToIncomingList(newArr,t);
					} else if(t.isRepeated()) {
						int res=t.getStartTime() + t.getRepeatInterval();
						if(res > from && res <= to) {
							addTaskToIncomingList(newArr,t);
						} else {
							while(res < t.getEndTime()) {
								res += t.getRepeatInterval();
								if ((t.getEndTime() - res) < 0) {
									break;
								} else if(res > from && res <= to) {
									addTaskToIncomingList(newArr,t);
									break;
								}
							}
						}
					}
				}
			}
		return newArr;
	}
	
	private void setTaskList(int i) {
		taskList = new Task[i];
	}
	
	private void addTaskToIncomingList(Task[] arr, Task t) {
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] == null) {
				arr[i] = t;
				break;
			}
		}
	}
	
	private void taskListCloningAndExpansion() {
		Task[] newList = new Task[taskList.length];
		for(int j = 0; j < newList.length; j++) {
			Task cloned = taskList[j].clone();
			newList[j] = cloned;
		}		
		setTaskList(newList.length + TASK_LIST_EXPANSION);
		for(int l = 0; l < newList.length; l++) {
			Task clonedTwo = newList[l].clone();
			taskList[l] = clonedTwo;
		}
	}
	
	private int getNeededLengthOfIncomingList(int from, int to) {
		int n = 0;
		for(Task t : taskList) {
			if(t != null && t.isActive()) {
				if(t.getStartTime() > from && t.getStartTime() <= to) {
					n = n + 1;
				} else if(t.isRepeated()) {
					int res=t.getStartTime() + t.getRepeatInterval();
					if(res > from && res <= to) {
						n+=1;
					} else {
						while(res < t.getEndTime()) {
							res += t.getRepeatInterval();
							if ((t.getEndTime() - res) < 0) {
								break;
							} else if(res > from && res <= to) {
								n+=1;
								break;
							}
						}
					}
				}
			}
		}
		return n;
	}
	
	private class ArrayListIterator implements Iterator<Task> {
		
		int index = 0;
		int prevIndex = 0;
		
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
			prevIndex = index;
			return taskList[index++];
		}
		
		@Override
		public void remove() {
			if(index > size)
				throw new NullPointerException("Attempt to remove a nonexistent list item");
			if(index <= 0 || index == prevIndex)
				throw new IllegalStateException("The next method has not yet been called, or the remove method has already been called after the last call to the next method");
			ArrayTaskList.this.remove(taskList[index-1]);
			index--;
		}
	}
}