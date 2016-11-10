package ua.edu.sumdu.ta.Nikolaj_Yekymov.pr5;

public class ArrayTaskList extends AbstractTaskList {
	
	protected static final int TASK_LIST_EXPANSION = 3;
	protected static final int LIST_SIZE = 10;
	public Task[] taskList = new Task[LIST_SIZE];
		
	public ArrayTaskList() {
		
		listsNumber++;
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

	public Task getTask(int index) {
		if(index < 0 || size() <= index)
			throw new IndexOutOfBoundsException("The argument of \"getTask\" method must fulfill the following conditions: >= 0 && < size");
		return taskList[index];
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
}