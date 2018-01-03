
var app = function() {

    var self = {};
    self.is_configured = false;
	
	self.black = 0;

	

    Vue.config.silent = false; // show all warnings

    // Extends an array
    self.extend = function(a, b) {
        for (var i = 0; i < b.length; i++) {
            a.push(b[i]);
        }
    };

    // Enumerates an array.
    var enumerate = function(v) {
        var k=0;
        v.map(function(e) {e._idx = k++;});
    };

    // Initializes an attribute of an array of objects.
    var set_array_attribute = function (v, attr, x) {
        v.map(function (e) {e[attr] = x;});
    };

    self.initialize = function () {
        document.addEventListener('deviceready', self.ondeviceready, false);
    };

    self.ondeviceready = function () {
        // This callback is called once Cordova has finished
        // its own initialization.
        console.log("The device is ready");
        $("#vue-div").show(); // This is jQuery.
        self.is_configured = true;
    };

    self.reset = function () {
        self.vue.board = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0];

		self.black = self.findBlack();
    };

    self.shuffle = function(i, j) {
		var temp = self.vue.board[self.black];
		var boardTemp = self.vue.board;
        // You need to implement this.
		
		if (i*4+j+4 == self.black || i*4+j-4 == self.black || 
			i*4+j+1 == self.black || i*4+j-1 == self.black){
				boardTemp[self.black] = boardTemp[i*4+j];
				self.black = i*4+j;
				boardTemp[i*4+j] = temp; 
				self.vue.board = [];
			
					
				console.log("low");	
				for (var k=0; k < boardTemp.length; k++){
					self.vue.board.push(boardTemp[k]);
				}
		}
		//self.vue.board[i*4+j]+4 > self.black || self.vue.board[i*4+j]-4 > self.black || 
				//self.vue.board[i*4+j]+1 > self.black || self.vue.board[i*4+j]-1 > self.black
		
		
        console.log("Shuffle:" + i + ", " + j);
    };
	
	self.scramble = function(){
		var temparray = self.mix();
		while(!self.solveCheck(temparray)){
			temparray = self.mix();
		}
		self.vue.board = [];
		self.vue.board = temparray;
		self.black = self.findBlack();
	};

    self.mix = function() {
        // Read the Wikipedia article.  If you just randomize,
        // the resulting puzzle may not be solvable.
		
		
		var currentIndex = 16;
		var temporaryValue;
		var randomIndex;
		var array = self.vue.board;

		while (0 !== currentIndex) {

			randomIndex = Math.floor(Math.random() * currentIndex);
			currentIndex--;
			temporaryValue = array[currentIndex];
			array[currentIndex] = array[randomIndex];
			array[randomIndex] = temporaryValue;
		}

		console.log(array)
		return array;
    };
	
	self.solveCheck = function(array){
		var parity = 0;
		var gwidth = 4;
		var row = 0
		var blackrow = 0;
		
		for(var q = 0; q < array.length; q++ ){
			if (q%gwidth == 0){
				row++
			}
			if (array[q]==0){
				blackrow = row;
				continue;
			}
			for (var w=q+1;w<array.length;w++){
				if (array[q]>array[w] && array[w]!=0){
					parity++;
				}
			}
		}
		if (blackrow%2==0){
			return parity%2 ==0;
		}else{
			return parity%2 !=0;
		}
		
		
	};
	
	
	
	self.findBlack = function(){
		for(var j=0; j < self.vue.board.length; j++){
			if (self.vue.board[j] == 0){
				return j;
			}
		}
		return 0;
	};

    self.vue = new Vue({
        el: "#vue-div",
        delimiters: ['${', '}'],
        unsafeDelimiters: ['!{', '}'],
        data: {
            board: []
        },
        methods: {
            reset: self.reset,
            shuffle: self.shuffle,
            scramble: self.scramble
        }

    });

    self.reset();

    return self;
};

var APP = null;

// This will make everything accessible from the js console;
// for instance, self.x above would be accessible as APP.x
jQuery(function(){
    APP = app();
    APP.initialize();
});
