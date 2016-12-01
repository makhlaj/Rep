import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.lang.*;

public class TaskXMLSerializer {

    private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private TransformerFactory transformerFactory = TransformerFactory.newInstance();
    private ArrayTaskList arrayList = new ArrayTaskList();
    private LinkedTaskList linkedList = new LinkedTaskList();
    private Task task;
    private Element rootElement, taskElement;
    private NamedNodeMap attributes;
    private Node tasks_root, timeAttribute, repeatedAttribute, activeAttribute, startAttribute, repeatAttribute, endAttribute;
    private NodeList task_node;
    private String taskTitle;
    int time, start, repeat, end;
    boolean isRepeated, isActive;

    public ArrayTaskList getArrayTaskList() {
        return arrayList;
    }

    public LinkedTaskList getLinkedTaskList() {
        return linkedList;
    }

    public void save(AbstractTaskList taskList, String file) {
        if(!(taskList instanceof AbstractTaskList))
            throw new IllegalArgumentException("The \"taskList\" argument is incorrect: (taskList instanceof AbstractTaskList) must return TRUE");
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            rootElement = doc.createElement ( "tasks");
            doc.appendChild(rootElement);
            for(Task task : taskList) {
                rootElement.appendChild(getTaskElement(doc, task));
            }
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult XMLfile = new StreamResult(new File("./src/main/resources/" + file + ".xml"));
            transformer.transform(source, XMLfile);

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        }
        catch (TransformerException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayTaskList load(String file) {
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("./src/main/resources/" + file + ".xml"));

            tasks_root = doc.getFirstChild();
            task_node = tasks_root.getChildNodes();
            for (int i = 1; i < task_node.getLength(); i++) {
                if(!task_node.item(i).hasAttributes())
                    continue;
                taskTitle = task_node.item(i).getTextContent();
                attributes = task_node.item(i).getAttributes();

                timeAttribute = attributes.getNamedItem("time");
                repeatedAttribute = attributes.getNamedItem("repeated");
                activeAttribute = attributes.getNamedItem("active");

                time = Integer.valueOf(timeAttribute.getNodeValue());
                isRepeated = Boolean.valueOf(repeatedAttribute.getNodeValue());
                isActive = Boolean.valueOf(activeAttribute.getNodeValue());

                if (!isRepeated) {
                    task = new Task(taskTitle,time);
                    task.setActive(isActive);
                    arrayList.add(task);
                    linkedList.add(task);
                }
                else {
                    startAttribute = attributes.getNamedItem("start");
                    repeatAttribute = attributes.getNamedItem("repeat");
                    endAttribute = attributes.getNamedItem("end");

                    start = Integer.valueOf(startAttribute.getNodeValue());
                    repeat = Integer.valueOf(repeatAttribute.getNodeValue());
                    end = Integer.valueOf(endAttribute.getNodeValue());

                    task = new Task(taskTitle,start,end,repeat);
                    task.setActive(isActive);
                    arrayList.add(task);
                    linkedList.add(task);
                }
            }
        }
        catch(ParserConfigurationException ex){
            ex.printStackTrace();
        }
        catch(SAXException ex){
            ex.printStackTrace();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        return arrayList;
    }

    private Node getTaskElement(Document doc, Task task) {
        taskElement = doc.createElement("task");
        taskElement.setAttribute("active",Boolean.toString(task.isActive()));
        taskElement.setAttribute("time", Integer.toString(task.getTime()));
        taskElement.setAttribute("start", Integer.toString(task.getStartTime()));
        taskElement.setAttribute("end", Integer.toString(task.getEndTime()));
        taskElement.setAttribute("repeat", Integer.toString(task.getRepeatInterval()));
        taskElement.setAttribute("repeated", Boolean.toString(task.isRepeated()));
        taskElement.appendChild(doc.createTextNode(task.getTitle()));
        return taskElement;
    }
}
