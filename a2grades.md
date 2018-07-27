# CS349 A2
Student: y293zhu
Marker: Jay Henderson


Total: 92 / 100 (92.00%)

Code: 
(CO: won’t compile, CR: crashes, FR: UI freezes/unresponsive, NS: not submitted)


Notes:   

# A2 Marking Scheme

In this assignment you'll demonstrate your understanding of MVC by implementing a sketching program that lets users draw on the screen using a mouse. Your drawing application will support standard drawing features (e.g. stroke width, multiple colors, window resizing). Additionally, your users can use a scrubber to scroll back or forward through the steps used to create a drawing.

You should make sure your program can run on the command line using `./gradlew run` on OSX or `gradlew.bat run` on Windows. Your program should run in IntelliJ and should use Java 10. You may use `vecmath.jar`.

## Basics (5%)

1. [3/3] Code compiles and runs.

2. [2/2] Include a README text file highlighting the enhancement that was implemented (may be in A2.md)

## Layout, Resizing, and Widgets (20%)

Layout, resizing and appropriate use of widgets (e.g. options enabled/disabled in UI when appropriate)

3. [4/4] The view has a menu bar with a file menu. Within File, the user can save and load doodles, create new doodles and exit the program.

4. [4/4] The view has a palette on the left hand side that supports color selection and stroke thickness/width. Colors and stroke width are displayed graphically (not as text). At least 6 colors should be displayed in the color palette. The currently selected color is indicated in some way.

5. [2/2] The view has a canvas as the main portion of the screen.

6. [4/4] At the bottom of the screen, there will be playback controls (JButtons with icons) and a playback bar (JSlider). Initially, there are no ticks on the bar. There should be a play button, start button, and end button. Each button should be drawn graphically (with images, no text labels) and should be disabled when it is appropriate.

7. [6/6] The canvas should be fully accessible regardless of the size of the window, by resizing itself to fit the available space. The color chooser and stroke chooser need to expand and contract based upon available space; how the layout changes is a visual design decision left to you. At the bottom of the screen, playback buttons must be positioned approximately as indicated, and the slider should expand and contract appropriately to fill available remaining space. You may set a "reasonable" minimum size (e.g. 400 X 300, 200 X 150, etc.). There should be no limit to the maximum size.


## Drawing (25%)

Functional requirements related to drawing (includes color palette, line selection, smooth drawing, file save and load).

8. [8/8] Users can save, load, create new doodles and exit the program through the file menu. When the user tries to create a new doodle, load an existing doodle, or exit the program, they should be prompted to save before overwriting any changes they've made. File saving and loading must be supported using a JFileChooser. You can save in either text or binary formats (you can just serialize an object, for example). Your JFileChooser should filter out just your supported data files (i.e. you should save using a file extension, and filter to show just those files).

9. [4/4] The last button in the color palette should invoke a JColorChooser to select a custom color. When you choose a color, that color will be used for any new strokes that you make. 

10. [3/3] The user can adjust stroke thickness. This thickness is applied to any new strokes.

11. [6/6] Users can draw smooth strokes on the canvas area. Mouse down begins a stroke, mouse dragged defines the body of a stroke, and mouse up defines the end of a stroke.

12. [4/4] When the user finishes drawing a stroke, the playbar ticks should update so there is a tick that corresponds to the end of each stroke.

## Animation and Playback (25%)

Functional requirements related to animation and playback (includes the scrubbing widget and functionality, timeline synchronization).

13. [9/9] The play button that causes the strokes to be drawn, from the current slider position to the end, as they were drawn on the canvas. The start button rewinds to the start of the animation, and the end button goes to the end of the animation (you are free to decide if rewinding automatically starts playing the animation, or if it just moves the scrubber).

14. [8/8] The playback bar must support scrubbing (the ability to move backward and forward through the animation). Minimally (for part marks) strokes may appear or disappear as you move the slider. For full marks, playback animation must include an aspect of time.

15. [6/8] If you scrub back, some of your later strokes will vanish on the display. At this point, if you begin drawing, the non-displayed strokes should be removed from your model and new strokes that you create will be appended to the visible strokes on the display. If you begin drawing partway through a previous stroke, that stroke will truncated to just include points up to the new point where you start drawing (and the scrubber will update based on your new line length). This is a simple form of undo/redo, as in on undo, if you start acting, future events (post-undo point) are deleted from the application model.

* -2 drawing partway through a previous stroke does not truncate the stroke

## MVC (15%)

Program architecture implemented as MVC; views stay coordinated.

16. [5/5] Multiple views implemented.

17. [0/2] The model is not coupled to specific view types.

* -2 Model is coupled to specific view types (not using View interface or Java Observer)

18. [1/3] The model and view communicate with one another properly.

* -1 model does not notify the View when it is updated
* -1 view does not properly respond to a notification from the model

19. [5/5] All views stay coordinated.

## Enhancements (10%)

20. [8/10] Implements one or more enhancements from the following list:

* When the canvas resizes to fit the available space, it maintains its aspect ratio (i.e. it can only be resized equally in both directions). The canvas is not cut off during resizing (5%). (3 marks because does not work all of the time)

* Ability to play animations both forward and backward (5 marks).
