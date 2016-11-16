package ua.edu.sumdu.ta.Nikolaj_Yekymov.pr6;

import java.io.*;
import java.util.*;

abstract public class AbstractTaskList implements Serializable, java.lang.Iterable<Task> {
	
	protected static final String BEGINNING_OF_LIST_TASK_TITLE = "[EDUCTR][TA] ";
	protected static int listsNumber;
	protected int size;
	
	public abstract void add(Task task);
	
	public abstract void remove(Task task);
	
	public abstract Task[] incoming(int from, int to);
	
	public int size() {
		return size;
	}
	
	@Override
	public AbstractTaskList clone() {
		try {
			AbstractTaskList clonedList = (AbstractTaskList)super.clone();
			return getDeepCloning(clonedList);
		}
		catch( CloneNotSupportedException ex) {
			ex.printStackTrace();
			throw new InternalError("CloneNotSupportedException in the \"clone\" method");
		}
	}

	@Override
	public String toString() {
		Iterator<Task> iter = this.iterator();
		String str = (this instanceof ArrayTaskList)?"\"ArrayTaskList [":"\"LinkedTaskList [";
		int i = 0;
		while(iter.hasNext()){
			if(i == size - 1) {
				str += iter.next().getTitle();
				break;
			}
			str += iter.next().getTitle() + ", ";
			i++;
		}
		str += "]\"";
		return str;
	}
	
	public boolean TaskListCompare(AbstractTaskList someList) {
		boolean result = false;
		String showResult = null;
		if(this instanceof LinkedTaskList && someList instanceof LinkedTaskList || 
		this instanceof ArrayTaskList && someList instanceof ArrayTaskList) {
			if(this.size() == someList.size()){
				Iterator<Task> thisIter = this.iterator();
				Iterator<Task> someIter = someList.iterator();
				while(thisIter.hasNext() && someIter.hasNext()){
					if(thisIter.next().equals(someIter.next()))
						result = true;
					else {
						System.out.println("TaskLists are not equals");
						return result = false;
					}
				}
			}
		}
		showResult = (result) ? "TaskLists are equals" : "TaskLists are not equals";
		System.out.println(showResult);
		return result;
	}
	
	private AbstractTaskList getDeepCloning(AbstractTaskList clonedList) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(clonedList);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (AbstractTaskList)ois.readObject();
		}
		catch(Exception ex) {
			ex.printStackTrace();
			throw new InternalError("Exception in the \"getDeepCloning\" method");
		}
	}
}