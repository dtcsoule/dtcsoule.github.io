clc
clear

%% Prompt data
a1 = input('Enter the length of the Link 1:\n');
%prompt = 'Enter the length of the Link 1:';
%answer1 = inputdlg(prompt);
%a1 = answer1;

a2 = input('Enter the length of the Link 2:\n');
%prompt = 'Enter the length of the Link 2:';
%answer2 = inputdlg(prompt);
%a2 = answer2;

x = input('What is the x coordinate value of the object:\n');
%prompt = 'What is the x coordinate value of the object:';
%answer_x = inputdlg(prompt);
%x = answer_x;

y = input('What is the y coordinate value of the object:\n');
%prompt = 'What is the y coordinate value of the object:';
%answer_y = inputdlg(prompt);
%y = answer_y;

%% Case

n = input('Enter 1 for pos OR 2 for neg thata:\n ');

switch (n)
    case 1
        % Inverse Kinematics Positive q2 value
        % The equation of q1 depends on q2
        q2 = acos(( x.^2 + y.^2 - a1.^2 - a2.^2 )./(2*a1*a2));
        q1 = atan(x/y)-(atan((a2 * sin(q2))./(a1 + a2 * cos(q2)))); 
        disp('Thata 2 (q2) below:');
        disp(q1),disp(q2);
        disp('Thata 1 (q1) above:\n');
    case 2
        % Inverse Kinematics negitive q2 value
        % The equation of q1 depends on q2
        q2 = -acos(( x.^2 + y.^2 - a1.^2 - a2.^2 )./(2*a1*a2));
        q1 = atan(x/y)+(atan((a2 * sin(q2))./(a1 + a2 * cos(q2))));
        disp('Thata 2 (q2) below:');
        disp(q1),disp(q2);
        disp('Thata 1 (q1) above:');
    otherwise
        errordlg('Only 1 or 2 please')
end

%% Animation

number_5 = '<a href = "https://dtcsoule.github.io/">2DOF robotic arm</a>';
is_alive = number_5;
disp('::::Click on link below for animation::::');
disp(is_alive);

%% Author info
me = msgbox('464 Robotics, Inverse Kinematics by: Dana Soule');


