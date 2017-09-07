# TerrariaClone

Back when I was first learning to program in Java, I decided to try to
make a clone of the excellent PC game [Terraria]. Of course, I was
convinced that my version would have many more features than the
official one.

But before I realized how silly that idea was, I produced 11,000 lines
of, to date, the most atrocious code I have ever seen in my life. I
make it available here mostly as a cautionary tale of what can happen
if you don't pay attention to the quality of your code. (Lesson
learned, in my case!) Here are some of the highlights:

* Blocks and items, among many other things, are represented by magic
  numbers instead of enums. There are 417 lines of block comments at
  the top of `TerrariaClone.java` that serve as manual translation
  tables.
* There are no access modifiers. Everything is package-private.
* So as to avoid needing to declare local variables and loop indices,
  all of the variables for everything are declared globally at the
  class level. Here is one of the several hundred lines of
  declarations in `TerrariaClone.java`:

    ```
    int x, y, i, j, k, t, wx, wy, lx, ly, tx, ty, twx, twy, tlx, tly, ux, uy, ux2, uy2, uwx, uwy, uwx2, ulx, uly, ulx2, uly2, ucx, ucy, uclx, ucly, pwx, pwy, icx, icy, n, m, dx, dy, dx2, dy2, mx, my, lsx, lsy, lsn, ax, ay, axl, ayl, nl, vc, xpos, ypos, xpos2, ypos2, x2, y2, rnum, mining, immune, width, height, xmin, xmax, ymin, ymax, intpercent, ground;
    ```

* Although there are a few other classes, the bulk of the code is in
  the God Class `TerrariaClone`, which spans over 6,500 lines of code.

* The `TerrariaClone.init()` method, which is over 1,300 lines long,
  actually grew so large that the Java compiler started *running out
  of memory* trying to compile it! The solution? Copy half of the
  `init()` code into a new method, called `codeTooLarge()`, and call
  that from `init()`.

* Frankly horrifying inline data tables:

    ```
    static boolean[] solid = {false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, true, true, true, false, false, false, true, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    static boolean[] ltrans = {false, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, false, false, false, false, false, false, false, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    static boolean[] wirec = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
    ```

* Over 1,000 lines of filling globally declared HashMaps and
  ArrayLists with magic numbers and strings one by one.

* The control flow is so labyrinthine that some of the code is
  actually indented by 23 tabs. Forget the 80-column rule -- these
  lines don't even *start* until column 92! Even if we discard the inline
  data tables, then the longest line in the codebase is still a
  whopping *387 characters* long (you'll have to scroll to the right
  to read it):

    ```
                                                                                                     blocks[l][y2][x2] >= 137 && blocks[l][y2][x2] <= 168 && y > y2 && blocks[l][y2][x2] != 140 && blocks[l][y2][x2] != 144 && blocks[l][y2][x2] != 148 && blocks[l][y2][x2] != 152 && blocks[l][y2][x2] != 156 && blocks[l][y2][x2] != 160 && blocks[l][y2][x2] != 164 && blocks[l][y2][x2] != 168) &&
    ```

* Did I mention that *everything is global*, including *loop indexing
  variables*?

* There are random print statements scattered throughout the codebase,
  with helpful messages like `[DEBUG2R]` and `[DEBUG2A]`.

* Why use a pre-existing GUI framework for your text boxes when you
  can easily roll your own?

    ```
    char c = 0;
    if (key.getKeyCode() == key.VK_Q) c = 'q';
    ...
    if (queue[5]) {
        if (c == 'q') c = 'Q';
        ...
    }
    ```

Now, when importing this project into version control (you thought I
was using version control when I wrote this??), I did fix a few of the
most blatantly horrible style violations -- for instance, I normalized
the whitespace, although other improvements (for example, removing the
over 500 cases of unnecessary boxing) have been reverted by popular
demand in the name of historical integrity. I also tried to get the
main game actually working, which meant fixing a few resource path
issues. Unfortunately, it's only working in the academic sense, since
it's so slow that you only get about 0.03 FPS and it crashes when you
click the mouse button. But, somewhere in there is a working game. You
know, sort of.

#### TerraFrame?

This project was originally called TerraFrame (back when I created it
in 2011). I believe the reasoning was that it was a *Terra*ria clone,
and it was in a J*Frame*. Yes, it's stupid, much like every other part
of this project. Meanwhile, there is a completely unrelated company
called TerraFrame. This led to an unfortunate misunderstanding wherein
somebody thought that this garbage heap of a codebase was associated
in some way with TerraFrame, the company. After receiving a very
polite email pointing out the issue, I've changed the name to
something that makes more sense (TerrariaClone).

[terraria]: https://terraria.org/
