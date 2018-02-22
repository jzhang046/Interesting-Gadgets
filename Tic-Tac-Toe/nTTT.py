from Board import Board

#Initialize
tttb = Board()

#Start playing.
winned = False
isX = True
while not winned:

    if isX:
        tttb.getNextX()
    else:
        tttb.getNextO()

    winned = tttb.checkWin()

    isX = not isX

#Playing ended.
#Here isX should be the one who lose.
if (isX):
    winner = tttb.OPlayer
else:
    winner = tttb.XPlayer

#Show the final board.
tttb.show()
print("Congratulations %s! You have won. " % winner)
