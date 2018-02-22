import sys
from blessings import Terminal
import numpy as np

class Display:

    term = None #blessings Terminal object.

    def __init__(self, size):
        self.term = Terminal()
        print(self.term.enter_fullscreen)
        for num in range(2 * size + 1):
            print("")

    def printBoard(self, board):

        rowIndex = -1
        colIndex = -1

        boardRows = board.shape[0]
        boardCols = board.shape[1]

        #Always print on the top.
        with self.term.location(0, 0):

            self.printTopButtLine(boardCols)

            for row in board:
                rowIndex += 1

                colPlaceCounter = 0;
                for value in np.nditer(row):

                    colIndex += 1
                    if value == 0:
                        #Further developments: handle more digits...?
                        sys.stdout.write('|%2d,%2d' % (rowIndex, colIndex))
                    elif value > 0:
                        sys.stdout.write('|' + self.term.on_bright_blue + \
                            '  X  ' + self.term.normal)
                    else:
                        #value < 0
                        sys.stdout.write('|' + self.term.on_bright_yellow + \
                            '  O  ' + self.term.normal)

                    #Every write occupy 6 positions.
                    colPlaceCounter += 6

                #End of informations...
                sys.stdout.write('|')
                colPlaceCounter += 1

                #Occupy the whole line.
                self.occupyTilEnd(colPlaceCounter)

                #Print seperation line in between or buttom.
                #Seperation lines were designed to have same length as data line,
                #   thus still passing colPlaceCounter here.
                if rowIndex < board.shape[0] - 1:
                    self.printMidSepLine(boardCols)
                else:
                    self.printTopButtLine(boardCols)

                colIndex = -1

            self.printTopButtLine(boardCols)

    def occupyTilEnd(self, colPlaceCounter):
        currentWidth = self.term.width
        while colPlaceCounter < currentWidth:
            sys.stdout.write(' ')
            colPlaceCounter += 1
        sys.stdout.write('\n')

    def printMidSepLine(self, colNum):
        #Input: number of cols.
        for tempNum in range(colNum):
            sys.stdout.write('|-----')
        sys.stdout.write('|')
        self.occupyTilEnd(colNum * 6 + 1)

    def printTopButtLine(self, colNum):
        for tempNum in range(colNum):
            sys.stdout.write('------')
        sys.stdout.write('-')
        self.occupyTilEnd(colNum * 6 + 1)

    def warning(self, warningStr):
        print(self.term.red + warningStr + self.term.normal)

    def showBold(self, boldStr):
        print(self.term.bold + self.term.underline + boldStr + self.term.normal)

    def exit(self):
        print(self.term.exit_fullscreen)
        sys.exit()
