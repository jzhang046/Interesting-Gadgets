import sys
from blessings import Terminal
import numpy as np

class Display:

    term = None

    def __init__(self):
        self.term = Terminal()
        print(term.enter_fullscreen)

    def printBoard(self, board):

        rowIndex = -1
        colIndex = -1

        self.printTopButtLine(board.shape[1])

        for row in board:
            rowIndex += 1

            for value in np.nditer(row):

                colIndex += 1
                if value == 0:
                    sys.stdout.write('|%2d,%2d' % (rowIndex, colIndex))
                elif value > 0:
                    sys.stdout.write('|' + self.term.on_bright_blue + \
                        '  X  ' + self.term.normal)
                else:
                    #value < 0
                    sys.stdout.write('|' + self.term.on_bright_yellow + \
                        '  O  ' + self.term.normal)

            sys.stdout.write('|\n')

            if rowIndex < board.shape[0] - 1:
                self.printMidSepLine(board.shape[1])
            else:
                self.printTopButtLine(board.shape[1])

            colIndex = -1

    def printMidSepLine(self, colNum):
        #Input: number of cols.
        for tempNum in range(colNum):
            sys.stdout.write('|-----')
        sys.stdout.write('|\n')

    def printTopButtLine(self, colNum):
        for tempNum in range(colNum):
            sys.stdout.write('------')
        sys.stdout.write('-\n')

    def warning(self, warningStr):
        print(self.term.red + warningStr + self.term.normal)
