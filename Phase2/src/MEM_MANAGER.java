/**
 * a. Sai Ram Thota
 * b. CS 4323
 * c. Phase Two
 * d. 11/29/2016
 * e. Global variables:
 *
 *      memAllocator : it is the partition arrayList representing memory.
 *      memIndex: Used HashMap to find the index of the stored variables.
 *      totalInternalFragmentation: used to calculate the final cumulative stat
 *      totalExternalFragmentation: used to calculate the final cumulative stat
 *
 *
 * f. MEM_MANAGER has fixed set of partitions which we will add and subtract when we want to
 * acquire or release.
 *
 * g.The MEM_Manager works perfect, as I tested it out with unit testing. It does
 * dynamically (acquire and check memory) in checkMem and releases the memory which
 * is called by J_TERM.
 *
 *
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MEM_MANAGER {

    public static double totalInternalFragmentation, totalExternalFragmentation;

    //memAllocations with 8 partitions and 26 fixed blocks.
    public static ArrayList<Integer> memAllocator =  new ArrayList<Integer>(Arrays.asList(8,8,8,8,12,12,12,12,
            18,18,18,18,18,18,32,32,32,32,32,32,52,60,60,60,60,128));


    //HashMap to keep track of indexes of the arrays
    public static HashMap<Integer, Integer> memIndex = new HashMap<Integer, Integer>();

    //check if the memory is available. If not, then add to diskList.
    public boolean checkMem(int jobNum, int reqMem)
    {
        boolean isMemAvailable = false;

        for (int i=0; i< memAllocator.size(); i++)
        {
            if(reqMem <= memAllocator.get(i) && memAllocator.get(i) != 0)
            {
                memAllocator.set(i, memAllocator.get(i) - reqMem);
                memIndex.put(jobNum,i);
                isMemAvailable = true;
                break;
            }

        }

        return isMemAvailable;
    }

    //Calculates internal and external fragmentation.
    public static void stats()
    {
        //I have used this duplicate of memAllocator to check the difference of the occupied memory.
        ArrayList<Integer> partitions =  new ArrayList<Integer>(Arrays.asList(8,8,8,8,12,12,12,12,
                18,18,18,18,18,18,32,32,32,32,32,32,52,60,60,60,60,128));

        double external=0, avgInternal;

        for(int i =0; i < memAllocator.size(); i++)
        {
            external = external + (partitions.get(i) - memAllocator.get(i));
        }

        avgInternal =  external/memAllocator.size();

        totalExternalFragmentation = totalExternalFragmentation + external;
        totalInternalFragmentation = totalInternalFragmentation + avgInternal;
    }

    //Releases the memory. Used after a particular job is terminated.
    public static void memRelease(int reqMem, int memIndex)
    {
            memAllocator.set(memIndex, reqMem + memAllocator.get(memIndex));

    }
}
