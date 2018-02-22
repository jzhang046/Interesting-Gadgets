import numpy as np
from Display import Display

class Board:

    size = -1 #Size of the board.
    board = None #Current status of the board.
    XPlayer = None #Name of player using 'X'
    OPlayer = None #Name of player using 'O
    lastPosi = None #Last player entered coordinates. [row, col].
    disp = None #Display object.

    def __init__(self):

        #Prompt for size of board. Minimal size should be 3.
        validInt = False
        while self.size < 3:

            if validInt:
                self.disp.warning("INVALID SIZE. Size should >= 3. ")

            try:
                self.size = int(raw_input("Enter board size n: "))
                validInt = True
            except KeyboardInterrupt:
                raise
            except:
                self.disp.warning("INVALID INPUT. Please enter a valid integer. ")
                validInt = False

        self.board = np.zeros((self.size, self.size), np.int8)

        #Prompt for players' name.
        self.XPlayer = raw_input("Enter name for player 1: ")
        self.OPlayer = raw_input("Enter name for player 2: ")

        #Enter full screen after getting required infomation.
        self.disp = Display()

    def show(self):
        self.disp.printBoard(self.board)

    def checkWin(self):
        #Would be called after getting valid self.lastPosi.

        rowPosi = self.lastPosi[0]
        colPosi = self.lastPosi[1]

        def getBoardValueAt(board, row, col):
            #Only for checkMin use.
            if row not in range(board.shape[0]) or col not in range(board.shape[1]):
                return 0
                #return value of 0 will always be different with putted position.
            else:
                return int(board[row, col])

        #Loop over -1, 0, 1 to check all positions around a position.
        #Win should always occur after one step around last placed position.
        for colOffset in range(-1, 2):
            for rowOffset in range(-1, 2):

                #Check if the index points to itself.
                if colOffset == 0 and rowOffset == 0:
                    continue
                else:

                    checkedValue = getBoardValueAt(self.board, \
                        rowPosi + rowOffset, colPosi + colOffset)
                    origValue = getBoardValueAt(self.board, \
                        rowPosi, colPosi)

                    if checkedValue != origValue:
                        #No need to check further. Skipping.
                        continue
                    else:
                        #Found 2 in a row. Checking 3nd position.
                        oppoValue = getBoardValueAt(self.board, \
                            rowPosi - rowOffset, colPosi - colOffset)
                        furtherValue = getBoardValueAt(self.board, \
                            rowPosi + 2 * rowOffset, colPosi + 2 * colOffset)

                        if oppoValue == origValue or furtherValue == origValue:
                            return True

        #After all looped position without returning true...
        return False

    def getNextX(self):
        self.lastPosi = self.getNext( \
            self.XPlayer + ", choose a coordinate to put a '"  + \
                self.disp.term.on_bright_blue + \
                "X" + self.disp.term.normal + \
                "': " )
        self.board[self.lastPosi[0], self.lastPosi[1]] += 1

    def getNextO(self):
        self.lastPosi = self.getNext( \
            self.OPlayer + ", choose a coordinate to put a '"  + \
                self.disp.term.on_bright_yellow + \
                "O" + self.disp.term.normal + \
                "': " )
        self.board[self.lastPosi[0], self.lastPosi[1]] -= 1

    def getNext(self, ppStr):

        isValid = False

        while not isValid:
            self.show()
            posiStr = raw_input(ppStr)

            try:
                row = int(posiStr.split(',')[0])
                col = int(posiStr.split(',')[1])
            except KeyboardInterrupt:
                raise
            except:
                self.disp.warning("INVALID INPUT. " + \
                    "Please enter 2 integers seperated by comma. ")
                isValid = False
                continue

            if row not in range(self.board.shape[0]) or \
                    col not in range(self.board.shape[1]):
                self.disp.warning("INVALID INPUT. Position out of range. ")
                isValid = False
                continue

            if self.board[row, col] != 0:
                self.disp.warning("INVALID INPUT. Occupied position. ")
                isValid = False
                continue

            isValid = True

        return [row, col]
