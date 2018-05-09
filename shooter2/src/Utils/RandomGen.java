/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juan De León
 */
public class RandomGen {
    /**
     * Get a Random value between 0 and n
     *
     * @param n Upper bound for Random value
     * @return random value
     */
    public static int randomVal(int n) {
        return (int) (Math.random() * n);
    }

    /**
     * Return a direction in rows
     *
     * @param n the desired direction in rows
     * @return the direction in rows
     */
    public static int randomR(int n) {
        int arr[] = {-1, 1, 0, 0};
        return arr[n];
    }

    /**
     * Return a direction in columns
     *
     * @param n the desired direction in columns
     * @return the direction in columns
     */
    public static int randomC(int n) {
        int arr[] = {0, 0, 1, -1};
        return arr[n];
    }

    /**
     * Generate a random Map
     *
     * @param tunnels ammount of tunnels
     * @param maxLength maximum length for the tunnels
     * @param n maximum row size
     * @param m maximum column size
     * @return random generated map
     */
    public static List<Integer>[] generate(int tunnels, int maxLength, int n, int m) {
        //Temporal map
        List<Integer>[] arr = new List[100];

        //Fill map with zeros
        for (int i = 0; i < n; i++) {
            arr[i] = new ArrayList<Integer>();
            for (int j = 0; j < m; j++) {
                arr[i].add(0);
            }
        }
        //Temporal data
        int lr = -2, lc = -2, randomR, randomC, currentRow = 0, currentCol = 0, tunnelsCreated = 0;

        //While the current tunnels are less than tunnels
        while (tunnelsCreated < tunnels) {
            do {
                //Generate a random value
                int valued = (int) (Math.random() * 4);

                //Get a direction for the generated random value
                randomR = randomR(valued); //randomDirection();
                randomC = randomC(valued);

                //While is not equal the last direction and not equal to the negative of the last one
            } while ((randomR == -lr && randomC == -lc) || (randomR == lr && randomC == lc));

            //Generate a random value between 1 and maxLength for the tunnel length
            int lengthForTunnel = randomVal(maxLength);
            int tunnelLength = 0;
            while (tunnelLength < lengthForTunnel) {
                //Break if out of boundaries
                if ((currentRow == 0 && randomR == -1) || (currentCol == 0 && randomC == -1) 
                || (currentRow == n - 1 && randomR == 1) || (currentCol == m - 1 && randomC == 1)) {
                    break;
                } else {
                    //Add a 1 in certain direction
                    arr[currentRow].set(currentCol, 1);
                    currentRow += randomR;
                    currentCol += randomC;
                    tunnelLength++;
                }
            }

            //if the tunnel Length is at least 1 then is a tunnel (It might be zero)
            if (tunnelLength > 0) {
                lr = randomR;
                lc = randomC;
                tunnelsCreated++;
            }

        }
        return arr;
    }

}
