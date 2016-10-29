package ua.edu.sumdu.ta.Nikolaj_Yekymov.pr3;

abstract class AbstractTaskList {
	
	public Task[] taskList = new Task[10];
	protected static int listsNumber;
	protected static final int taskListExpansion = 3;
	protected static final String beginningOfListTaskTitle = "[EDUCTR][TA] ";
	
	public static int getListsNumber() {
		return listsNumber;
	}
	
	public int getTaskListExpansion() {
		return taskListExpansion;
	}
	
	abstract void add(Task task);
	
	abstract void remove(Task task);
	
	abstract Task getTask(int index);
	
	public int size() {
		int a = 0;
		for(int i = 0; i < taskList.length; i++) {
			if(taskList[i] != null) {
				a++;
			}
		}
		return a;
	}
	
}