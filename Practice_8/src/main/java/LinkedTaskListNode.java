import java.io.*;

public class LinkedTaskListNode implements Cloneable, Serializable {
	
	public Task value;
	public LinkedTaskListNode next;
	public LinkedTaskListNode previous;
	
	public LinkedTaskListNode(Task value)
    {
        this.value = value;
    }
}