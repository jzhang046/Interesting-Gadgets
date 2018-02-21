import sys
import numpy as np

def printBoard(board): 
    
    rowIndex = -1
    colIndex = -1
    
    printTopButtLine(board.shape[1])

    for row in board: 
        rowIndex += 1

        for value in np.nditer(row): 
            
            colIndex += 1
            if value == 0: 
                sys.stdout.write('|%2d,%2d' % (rowIndex, colIndex))
            elif value > 0: 
                sys.stdout.write('|  X  ')
            else: 
                #value < 0
                sys.stdout.write('|  O  ')

        sys.stdout.write('|\n')

        if rowIndex < board.shape[0] - 1: 
            printMidSepLine(board.shape[1])
        else: 
            printTopButtLine(board.shape[1])       

        colIndex = -1


def printMidSepLine(colNum): 
    #Input: number of cols. 
    for tempNum in range(colNum): 
        sys.stdout.write('|-----')
    sys.stdout.write('|\n')
 
def printTopButtLine(colNum): 
    for tempNum in range(colNum): 
        sys.stdout.write('------')
    sys.stdout.write('-\n')

