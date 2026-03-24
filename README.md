# A6 Card Sorting
CSC 210: Data Structures

## General Information

Your readme should include the following information.

Your name: Michelle Jiang

Other collaborators: 

Was anyone particularly helpful? Give them a shout-out here:

## References

References used (besides JavaDoc and course materials): 

If you used AI at all for this assignment: How did you use it? What did this experience teach you?

## Reflection Questions

What did you notice about the differences in runtime across algorithms as you changed the number of cards you were sorting? If you had to split them into "slower" algorithms vs "faster" algorithms, which would you put in each category?

As the number of cards increased, insertion sort and selection sort slowed down a lot while merge sort was relatively faster. With 10k cards, insertion and selection sort were both about 100ms while at 40k cards they were about 2000ms. Merge sort was mostly the same across the ranges. Linear search stayed about the same as well because it scans once only. 

Therefore the insertion sort and selection sort are slower algorithms while merge sort is faster. 

cryst@X1-Carbon-Gen13 MINGW64 ~/.vscode/CSC210-A6 (main)
$ time java AlgorithmTimingRunner linear 10000
linear on 10000 cards took 8.778 ms

real    0m0.210s
user    0m0.000s
sys     0m0.046s

cryst@X1-Carbon-Gen13 MINGW64 ~/.vscode/CSC210-A6 (main)
$ time java AlgorithmTimingRunner insertion 10000
insertion on 10000 cards took 92.613 ms

real    0m0.267s
user    0m0.047s
sys     0m0.016s

cryst@X1-Carbon-Gen13 MINGW64 ~/.vscode/CSC210-A6 (main)
$ time java AlgorithmTimingRunner selection 10000
selection on 10000 cards took 150.642 ms

real    0m0.344s
user    0m0.015s
sys     0m0.030s

cryst@X1-Carbon-Gen13 MINGW64 ~/.vscode/CSC210-A6 (main)
$ time java AlgorithmTimingRunner merge 10000
merge on 10000 cards took 14.430 ms

real    0m0.181s
user    0m0.000s
sys     0m0.047s

cryst@X1-Carbon-Gen13 MINGW64 ~/.vscode/CSC210-A6 (main)
$ time java AlgorithmTimingRunner linear 20000
linear on 20000 cards took 8.187 ms

real    0m0.210s
user    0m0.031s
sys     0m0.015s

cryst@X1-Carbon-Gen13 MINGW64 ~/.vscode/CSC210-A6 (main)
$ time java AlgorithmTimingRunner insertion 20000
insertion on 20000 cards took 441.982 ms

real    0m0.633s
user    0m0.015s
sys     0m0.031s

cryst@X1-Carbon-Gen13 MINGW64 ~/.vscode/CSC210-A6 (main)
$ time java AlgorithmTimingRunner selection 20000
selection on 20000 cards took 638.926 ms

real    0m0.812s
user    0m0.000s
sys     0m0.030s

cryst@X1-Carbon-Gen13 MINGW64 ~/.vscode/CSC210-A6 (main)
$ time java AlgorithmTimingRunner merge 20000
merge on 20000 cards took 16.884 ms

real    0m0.187s
user    0m0.031s
sys     0m0.000s

cryst@X1-Carbon-Gen13 MINGW64 ~/.vscode/CSC210-A6 (main)
$ time java AlgorithmTimingRunner linear 40000
linear on 40000 cards took 8.071 ms

real    0m0.205s
user    0m0.015s
sys     0m0.000s

cryst@X1-Carbon-Gen13 MINGW64 ~/.vscode/CSC210-A6 (main)
$ time java AlgorithmTimingRunner insertion 40000
insertion on 40000 cards took 2044.868 ms

real    0m2.220s
user    0m0.000s
sys     0m0.000s

cryst@X1-Carbon-Gen13 MINGW64 ~/.vscode/CSC210-A6 (main)
$ time java AlgorithmTimingRunner selection 40000
selection on 40000 cards took 2691.762 ms

real    0m2.906s
user    0m0.031s
sys     0m0.015s

cryst@X1-Carbon-Gen13 MINGW64 ~/.vscode/CSC210-A6 (main)
$ time java AlgorithmTimingRunner merge 40000
merge on 40000 cards took 24.277 ms

real    0m0.216s
user    0m0.031s
sys     0m0.015s


After implementing these algorithms with linked-list-style operations, which methods would have benefited most from using `ArrayList` instead of `LinkedList`, and why? Which methods do you think were a better fit for linked lists?

I think insertion sort and selection sort would have benefited the most from an Arraylist compared to a LinkedList because they require locating elements multiple times, which for Arraylists are typically O(1) operations while for LinkedLists they're O(n). 

Merge sort I think was better for linked lists because it is mostly removing from the front and adding to the back, which is efficient for linkedlists. 

Why didn't we implement binary search for this assignment?

Binary search wasn't implemented because you need to access the middle of a list. Using a linked list to find the middle element would require iterating through the list every time which would make it terribly inefficient. 

## Reflection

What was your overall experience with this assignment? What was most challenging? What was most interesting?

I liked this assignment--I watch a lot of youtube shorts about sorting so this was really nice to see how this works in practice. I found merge sorting incredibly challenging, I felt that insertion sort and selection sort in practice do kind of similar things (in terms of code) while merge sort was much more difficult to write. I found merge sort the most interesting for the same reason. 