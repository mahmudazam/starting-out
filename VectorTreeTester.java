import java.util.*;
import java.lang.*;

public class VectorTreeTester {
	public static void main(String[] args) {
		Scanner omi = new Scanner(System.in);
		VectorTree tree = new VectorTree();
		tree.addObject(new Vector(0,1)); //0
		tree.addObject("object2"); //1
		tree.addObject("object3"); //2
		tree.addObject(new int[]{0}, "object4");//0,0
		tree.addObject(new int[]{0}, new Vector(0,1)); //0,1
		tree.addObject(new int[]{0,1}, new Vector(0,1)); //0,1,0
		tree.addObject(new int[]{0,1,0}, "object7");// 0,1,0,0
		int[] index = {0,1,0,0};
		//int[] index = tree.indexListFromCommandLine(args[0]);
		System.out.println(tree.getObject(index));
		//System.out.println(args[0]);
		//System.out.println(args[0].length());
		//for(int printer: index) {
		//	System.out.println(printer);
		//}
		//System.out.println((int) 0);
		
	}
}

//--------------------------------------------------------------------------------

class VectorTree {
	private Vector root = null, currentNode = null;
	
	public VectorTree() {
		root = new Vector(0,1);
		currentNode = root;
	}
	
	public int[] indexListFromCommandLine(String arg) {// This causes problems: ArrayIndexOutOfBoundsException
		int[] index = new int[(arg.length()+1)/2];
		int j = 0;
		for(int i =0; i < arg.length(); i = i+2) {
			index[j] = (int) arg.charAt(i);
			j++;
		}
		return index;
	}
	
	public void addObject(Object object) {
			currentNode.addElement(object);
	}
	
	public void addObject(int[] indexOfParent, Object object) {	
		Vector parent = null;
		try {
			parent = (Vector) getObject(indexOfParent);
			parent.addElement(object);
		} catch (ClassCastException cannotCast) {
			Vector newVectorNode = new Vector(0,1);
			newVectorNode.addElement(getObject(indexOfParent));
			newVectorNode.addElement(object);
			int targetIndex = indexOfParent[indexOfParent.length -1]; 
			indexOfParent = getParentIndex(indexOfParent);
			parent = (Vector) getObject(indexOfParent);
			parent.set(targetIndex, newVectorNode);
		}
	}
	
	public int[] getParentIndex(int index[]) {
		int[] indexOfParent = new int[(index.length -1)];
		for(int i = 0; i < indexOfParent.length; i++) {
			indexOfParent[i] = index[i];
		}
		return indexOfParent;
	}
	
	public Object getObject(int[] index) {
		int targetIndex = index[index.length - 1];
		index = getParentIndex(index);
		this.currentNode = this.root;
		for (int i = 0; i < index.length; i++) {
			if(currentNode instanceof Vector) {
				currentNode = (Vector) currentNode.get(index[i]);
			} else if(!(currentNode instanceof Vector)) {
				try {
					currentNode = (Vector) currentNode.get(index[i]);
				} catch (ClassCastException cannotCast) {
					return currentNode;
				}
			}
		}
		return currentNode.get(targetIndex);
	}
}
