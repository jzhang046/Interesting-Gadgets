import numpy as np 
import Display as D

#Get the board size. 
size = -1
validInt = False
while size < 3: 

    if validInt: 
        print("INVALID SIZE. Size should >= 3. ")
        
    try: 
        size = int(raw_input("Enter board size n: "))
        validInt = True
    except KeyboardInterrupt: 
        raise
    except: 
        print("INVALID INPUT. Please enter a valid integer. ")
        validInt = False

board = np.zeros((size, size), np.int8)
D.printBoard(board)

winned = False
isX = True
while not winned: 
    
    #TODO: error handling for raw_input. 
    if isX: 
        posiStr = raw_input("%s, choose a coordinate to put a 'X': " % 'X')
    else: 
        posiStr = raw_input("%s, choose a coordinate to put a 'O': " % 'O')

    rowPosi = int(posiStr.split(',')[0])
    colPosi = int(posiStr.split(',')[1])

    #TODO: check if it's a occupied place. 
    if isX: 
        board[rowPosi, colPosi] += 1
    else: 
        board[rowPosi, colPosi] -= 1

    D.printBoard(board)

    isX = not isX
