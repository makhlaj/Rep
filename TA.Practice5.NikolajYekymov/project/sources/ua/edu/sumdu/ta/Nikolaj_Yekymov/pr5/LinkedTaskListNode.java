package ua.edu.sumdu.ta.Nikolaj_Yekymov.pr5;

public class LinkedTaskListNode {
	
	public Task value;
	public LinkedTaskListNode next;
	public LinkedTaskListNode previous;
	
	public LinkedTaskListNode(Task value)
    {
        this.value = value;
    }
}