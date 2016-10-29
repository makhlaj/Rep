package ua.edu.sumdu.ta.Nikolaj_Yekymov.pr3;

import ua.edu.sumdu.ta.Nikolaj_Yekymov.pr3.ArrayTaskList;

/**
 * This class describes the data type "Task", which contains information about the essence of the task, 
 * its status (active / inactive), time interval, through which it is needed to repeat the notification about it
 * @version    1.0    20 Oct 2016
 * @author    Nikolaj Yekymov
 */
public class Task implements Cloneable {
	
	private String title;
	private boolean isActive,isRepeated;
	private int time, start, end, repeat;

	public Task() {}
	
   /**
	* call setTitle method
	* call setTime method
	* @param title title variable initialization
	* @param time time variable initialization
	*/
	public Task(String title, int time) {
		setTitle(title);
		setTime(time);
	}
	
   /**
	* call setTitle method
	* call setTime method
	* @param title title variable initialization
	* @param start start variable initialization
	* @param end end variable initialization
	* @param repeat repeat variable initialization
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
	* @param title title variable initialization
	*/
	public void setTitle(String title) {
		if (title != null) {
			this.title = title;
		} else {
			System.out.println("Invalid argument for setTitle method");			
		}
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
	* @param active $isActive variable initialization
	*/
	public void setActive(boolean active) {
		isActive = active;
	}

   /**
	* Set notification time for the Task
	* @param time time variable initialization
	* isRepeated variable initialization
	*/
	public void setTime(int time) {
		if (time > 0){
			isRepeated = false;
			this.time = time;
		} else {
			System.out.println("Invalid argument for setTime method of Task - " + this.title);
		}
	}

   /**
	* Set start, end, repeat interval of the notification time for the Task
	* @param start start variable initialization
	* @param end end variable initialization
	* @param repeat repeat variable initialization
	* isRepeated variable initialization
	*/
	public void setTime(int start, int end, int repeat) {
		if (start >= 0 && start < end) {
			isRepeated = true;
			this.start = start;
			this.end = end;
			this.repeat = repeat;
		} else {
			System.out.println("Invalid arguments for setTime method of Task - " + this.title);
		}
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
	* @param time specified time 
	*/
	public int nextTimeAfter(int currentTime) {
		int res = -1;
		if (currentTime >= 0) {
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
		Task objTask = null;
		if (obj instanceof Task) {
			objTask = (Task) obj;
		}
		else return false;
		if (getTime() != objTask.getTime() || getStartTime() != objTask.getStartTime() || 
			getEndTime() != objTask.getEndTime() || getRepeatInterval() != objTask.getRepeatInterval() ||
			isRepeated() != objTask.isRepeated() || getTitle() != objTask.getTitle())
			return false;
		return true;
	}
	
	public static void main(String[] args) {
		
	}
}