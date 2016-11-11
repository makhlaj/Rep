package ua.edu.sumdu.ta.Nikolaj_Yekymov.pr5;

public class LinkedTaskList extends AbstractTaskList {
	
	private LinkedTaskListNode head;
	private LinkedTaskListNode tail;
	
	public LinkedTaskList() {
		
		listsNumber++;
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
	
	public Task getTask(int index) {
		if (index < 0 || size() <= index) 
			throw new IndexOutOfBoundsException("The argument of \"getTask\" method must fulfill the following conditions: >= 0 && < size");
		if (index <= (size-1)/2) {
			int i = 0;
			LinkedTaskListNode current = head;
			while (current != null && index != i) {	 
				current = current.next;
				i++;
			}
			return current.value;		
		} else {
			int j = size-1;
			LinkedTaskListNode current = tail;
			while (current != null && index != j) {	 
				current = current.previous;
				j--;
			}
			return current.value;
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
}