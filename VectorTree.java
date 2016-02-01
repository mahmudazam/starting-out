import java.util.*;
import java.lang.*;

public class VectorTree {
	public Vector root = null, currentNode = null;
	public VectorTree() {
		root = new Vector(0,1);
		currentNode = root;
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
		try { 
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
			return 	currentNode.get(targetIndex);
		} catch(ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	public int[] stringArgsToInts(String[] args) {
		Vector argsInt = new Vector(0, 1);
		int number, tens, charInt;
		char argsReader;
		outerLoop:
		for (int i = 0; i < args.length; i++) {
			number = 0;
			tens = (int)Math.pow(10, (args[i].length() - 1));
			for(int j = 0; j < args[i].length(); j++) {
				try{
					charInt = (int)args[i].charAt(j) - 48;
					if((charInt<0)||(charInt>9)) continue outerLoop;
					number = number + tens*charInt;
					//System.out.println(number + " " + tens);
					//System.out.println((int)args[i].charAt(j) - 48);
					if(tens!= 1) tens /= 10;
				} catch(Exception e) {
					continue;
				}
			}
			argsInt.addElement(number);
		}
		int[] a = new int[argsInt.size()];
		for(int i = 0; i < argsInt.size(); i++) {
			a[i] = (int) argsInt.elementAt(i);
		}
		return a;
	}
	public void printTree() {
		
	}
}
