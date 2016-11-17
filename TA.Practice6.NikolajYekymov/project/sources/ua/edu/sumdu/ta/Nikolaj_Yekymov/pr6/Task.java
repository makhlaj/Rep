package ua.edu.sumdu.ta.Nikolaj_Yekymov.pr6;

import ua.edu.sumdu.ta.Nikolaj_Yekymov.pr6.ArrayTaskList;
import ua.edu.sumdu.ta.Nikolaj_Yekymov.pr6.LinkedTaskList;
import java.io.*;
import java.util.*;

/**
 * This class describes the data type "Task", which contains information about the essence of the task, 
 * its status (active / inactive), time interval, through which it is needed to repeat the notification about it
 * @version    6.0    17 Oct 2016
 * @author    Nikolaj Yekymov
 */
public class Task implements Cloneable, Serializable {
	
	private String title;
	private boolean isActive,isRepeated;
	private int time, start, end, repeat;

	public Task() {}
	
   /**
	* call setTitle method
	* call setTime method
	* @param title for the title variable initialization
	* @param time for the time variable initialization
	*/
	public Task(String title, int time) {
		setTitle(title);
		setTime(time);
	}
	
   /**
	* call setTitle method
	* call setTime method
	* @param title for the title variable initialization
	* @param start for the start variable initialization
	* @param end for the end variable initialization
	* @param repeat for the repeat variable initialization
	*/
	public Task(String title, int start, int end, int repeat) {
		setTitle(title);
		setTime(start, end, repeat);
	}
	
   /**
	* @return value of title variable
	*/
	public String getTitle() {
		return title;
	}
	
   /**
	* @param title for the title variable initialization
	*/
	public void setTitle(String title) {
		if (title == null || title.isEmpty())
			throw new IllegalArgumentException("The argument of \"setTitle\" method cannot be null and empty");
		this.title = title;
	}
	
   /**
	* @return value of isActive variable
	* isActive - status of the Task(active / inactive)
	*/	
	public boolean isActive() {
		return isActive;	
	}
	
   /**
	* Set status for the Task
	* @param active for the isActive variable initialization
	*/
	public void setActive(boolean active) {
		isActive = active;
	}

   /**
	* Set notification time for the Task
	* @param time for the time variable initialization
	* isRepeated variable initialization
	*/
	public void setTime(int time) {
		if (time < 0)
			throw new IllegalArgumentException("The argument of \"setTime\" method cannot be less then zero");
		isRepeated = false;
		this.time = time;
	}

   /**
	* Set start, end, repeat interval of the notification time for the Task
	* @param start for the start variable initialization
	* @param end for the end variable initialization
	* @param repeat for the repeat variable initialization
	* isRepeated variable initialization
	*/
	public void setTime(int start, int end, int repeat) {
		if (start < 0 || start >= end || repeat < 0)
			throw new IllegalArgumentException("The arguments of \"setTime\" method must fulfill the following conditions: start >= 0 && start < end && repeat >= 0");
		isRepeated = true;
		this.start = start;
		this.end = end;
		this.repeat = repeat;
	}
	
   /**
	* @return value of time variable (for not repeated Task)
	* @return value of start variable (for repeated Task)
	*/
	public int getTime() {
		return (isRepeated)? start : time;
	}

   /**
	* @return value of time variable (for not repeated Task)
	* @return value of start variable (for repeated Task)
	*/
	public int getStartTime() {
		return (!isRepeated)? time : start;
	}
	
   /**
	* @return value of time variable (for not repeated Task)
	* @return value of end variable (for repeated Task)
	*/
	public int getEndTime() {
		return (!isRepeated)? time : end;
	}
	
   /**
	* @return value of repeat variable (for repeated Task)
	* @return 0 (for not repeated Task)
	*/
	public int getRepeatInterval() {
		return (!isRepeated)? 0 : repeat;
	}

   /**
	* @return value of isRepeated variable
	*/
	public boolean isRepeated() {
		return isRepeated;
	}
	
   /**
	* @return description of the Task
	*/
	@Override
	public String toString() {
		String task = "Task \"" + getTitle();
		if (isActive) {
			return (!isRepeated) ? task + "\" at " + getTime() : 
			task + "\" from " + getStartTime() + " to " + getEndTime() + " every " + getRepeatInterval()+" seconds";
		}
		else return task + "\" is inactive";
	}
	
   /**
	* @return the next notification time regarding the specified time
	* @param currentTime specified time 
	*/
	public int nextTimeAfter(int currentTime) {
		int res = -1;
		if (currentTime < 0)
			throw new IllegalArgumentException("The argument of \"nextTimeAfter\" method cannot be less then zero");
		if (isActive) {
			if (isRepeated) {
				if (currentTime >= start && currentTime < end) {
				  /*res = (((currentTime - start)/repeat)+1)*repeat + start;
					if(res > end){res = -1;}*/
					res = start + repeat;
					while(currentTime >= res) {
						res += repeat;
						if ((end - res) < 0) {
							res = -1;
							break;
						}
					}
				} else if (currentTime < start) {
					res = start;
				}
			} else if ((time - currentTime) > 0) {
					res = time;
			}
		}
		return res;
	}
	
	@Override
	public Task clone() {
		try {
			return (Task)super.clone();
		}
		catch( CloneNotSupportedException ex ) {
			throw new InternalError();
		}
	}
	
	@Override
	public int hashCode() {
		int result = 1000 + getTime() + getStartTime() + getEndTime() + getRepeatInterval();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj.hashCode() != this.hashCode())
			return false;
		Task objTask = null;
		if (obj instanceof Task) {
			objTask = (Task) obj;
		}
		else return false;
		if (getTime() != objTask.getTime() || getStartTime() != objTask.getStartTime() || 
			getEndTime() != objTask.getEndTime() || getRepeatInterval() != objTask.getRepeatInterval() ||
			isRepeated() != objTask.isRepeated() || isActive() != objTask.isActive() ||
			(getTitle().compareTo(objTask.getTitle()) != 0))
			return false;
		return true;
	}
	
	public static void main(String[] args) {
		
	}
}