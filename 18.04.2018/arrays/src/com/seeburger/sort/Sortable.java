package com.seeburger.sort;

public interface Sortable {
	
	public static void sort(int[] arr)
	{
		
	}
	
	public static void swap(int [] arr, int index1, int index2) {
			
			int temp = arr[index1];
			arr[index1] = arr[index2];
			arr[index2] = temp;
			
		}

}
