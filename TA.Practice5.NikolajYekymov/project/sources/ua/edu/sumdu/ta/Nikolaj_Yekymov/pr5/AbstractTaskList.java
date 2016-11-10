package ua.edu.sumdu.ta.Nikolaj_Yekymov.pr5;

abstract public class AbstractTaskList {
	
	protected static final String BEGINNING_OF_LIST_TASK_TITLE = "[EDUCTR][TA] ";
	protected static int listsNumber;
	protected int size;
	
	public abstract void add(Task task);
	
	public abstract void remove(Task task);
	
	public abstract Task getTask(int index);
	
	public abstract Task[] incoming(int from, int to);
	
	public int size() {
		return size;
	}
	
}