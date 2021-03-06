var World = {
	loaded: false,

	init: function initFn() {
		this.createOverlays();
	},

	createOverlays: function createOverlaysFn() {
		// Initialize ClientTracker
		this.tracker = new AR.ClientTracker("assets/tracker.wtc", {
			onLoaded: this.worldLoaded
		});

		/*
			Create a transparent video drawable:
			This bonus example shows you how to add transparent videos on top of a target. Transparent videos require some extra preparation work.

			Summarizing the required steps, here is what you need to do in order to use transparent videos in a simple list. We are describing each of the steps in more detail.

			1.	Produce a green screen (chroma key) video
			2.	Edit that video using standard video processing software
				and remove the green screen. Export your result into a file format,
				which can handle alpha channel information (e.g. Apple PreRes 4444)
			3.	Convert the video from step 2 using the script in the tools folder
			4.	Add it to a target image

			Producing a transparent video is usually done using a green screen for filming and a technique called chroma key to replace the green background with transparency. Extensive information is available on the internet that should help you get started on this topic.

			There are different video codecs that support alpha channels for motion picture and most of them will work as your raw material. We have extensively tested Apple ProRes 4444 codec for our own development and were satisfied with the results.

			The Wikitude SDK can render H.264 encoded videos, which is a codec that in practice does not support an alpha channel. The solution here is to include in the alpha channel in a separate (visible) part of the video. The video is split vertically consisting of a color and a alpha channel in the final video below each other.

			The upper half of the image transports the color information for the final video while the lower half includes a grayscale representation of the alpha layer. White areas will be fully opaque and black areas will be fully transparent. If you are familiar with Photoshop, think of the lower half as a mask. Resulting videos have an height that is exactly twice as big as the input video.

			To convert your raw video to a valid input video for the SDK we need to re-encode the video and automatically create the alpha mask. The script below uses ffmpeg to do so and wraps the necessary commands. Follow these simple steps:

			MacOS X
			Open the Terminal application
			Input cd <SDK>/tools/video/MacOSX. Replace <SDK> with the path to the SDK folder
			Execute sh convert.sh <input video> <output video>. Replace <input video> with the path to your transparent video and <output video> with the path where you want the output video to be stored.

			Windows
			Open the Command Line
			cd <SDK>\tools\video\Windows. Replace <SDK> with the path to the SDK folder
			Execute convert.bat <input video> <output video>. Replace <input video> with the path to your transparent video and <output video> with the path where you want the output video to be stored.
			This creates the required video with a vertically split color and alpha channel.

			Adding the transparent H.264 video to a target image is easy and accomplished in the same way as any other video is added. Just make sure to set the isTransparent property of the AR.VideoDrawable to true.
		*/
		var video1 = new AR.VideoDrawable("assets/out_sat.mp4", 0.7, {
			offsetX: 0.5,
			offsetY: 0.12,
			isTransparent: true
		});

		var video2 = new AR.VideoDrawable("assets/output2.mp4", 0.7, {
			offsetX: 0.5,
			offsetY: 0.12,
			isTransparent: true
		});
		video1.play(-1);
		video1.pause();
		video2.play(-1);
        video2.pause();

		/*
			Adding the video to the image target is straight forward and similar like adding any other drawable to an image target.

			Note that this time we use "*" as target name. That means that the AR.Trackable2DObject will respond to any target that is defined in the specified tracker. You can use wildcards to specify more complex name matchings. E.g. 'target_?' to reference 'target_1' through 'target_9' or 'target*' for any targets names that start with 'target'.
		*/
		var page1 = new AR.Trackable2DObject(this.tracker, "g-shock", {
			drawables: {
				cam: [video1]
			},
			onEnterFieldOfVision: function onEnterFieldOfVisionFn() {

					video1.resume();

				var e = document.getElementById('loadingMessage');
				e.innerHTML = "Sathish is speaking about greenmat";
			},
			onExitFieldOfVision: function onExitFieldOfVisionFn() {
				video1.pause();
				var e = document.getElementById('loadingMessage');
                e.innerHTML = "Scan the Kiosk screen to get Information!";
			}
		});

		var page2 = new AR.Trackable2DObject(this.tracker, "seiko", {
			drawables: {
				cam: [video2]
			},
			onEnterFieldOfVision: function onEnterFieldOfVisionFn() {
				video2.resume();
				var e = document.getElementById('loadingMessage');
                e.innerHTML = "Tushar is making his first attempt";
			},
			onExitFieldOfVision: function onExitFieldOfVisionFn() {
				video2.pause();
				var e = document.getElementById('loadingMessage');
                e.innerHTML = "Scan the Kiosk screen to get Information!";
			}
		});


	},



	worldLoaded: function worldLoadedFn() {
/*		var cssDivInstructions = " style='display: table-cell;vertical-align: middle; text-align: right; width: 50%; padding-right: 15px;'";
		var cssDivSurfer = " style='display: table-cell;vertical-align: middle; text-align: left; padding-right: 15px; width: 38px'";
		var cssDivBiker = " style='display: table-cell;vertical-align: middle; text-align: left; padding-right: 15px;'";
		document.getElementById('loadingMessage').innerHTML =
			"<div" + cssDivInstructions + ">Scan Target &#35;1 (surfer) or &#35;2 (biker):</div>" +
			"<div" + cssDivSurfer + "><img src='assets/surfer.png'></img></div>" +
			"<div" + cssDivBiker + "><img src='assets/bike.png'></img></div>";*/

		// Remove Scan target message after 10 sec.
		/*setTimeout(function() {
			var e = document.getElementById('loadingMessage');
			e.parentElement.removeChild(e);
		}, 10000);*/
	}
};

World.init();
