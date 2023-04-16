# App Structure

### USER 
Login made using email and password
Fields 
- email
- password
- firstName, lastName
- role (user, owner, admin)
- validation token and if is enabled
- company ( company class)

### Company
The company delegate, first enrolls the company and grants the user owner privileges
"owner" user who will create the company users and sets the role for every user.
OWNER can access all the Company sections including reports.

### Instructor
Access all the sections that a normal user has privileges.
Courses and reports specific for Instructors

# Class (entities) structure

### Company
- id
- name
- CIF
- address
- email
- set of course numbers

### Person

- id 
- name
- CNP
- address
- email
- phone
 table relation : many to many (by ID)

### Instructor ( extends Person)
- id
- badgeNumber
- 

[comment]: <> (- person_id &#40;person class&#41;)
- company_id (company class)
- course_id (course class) - multiple unique courses

### Course
- id
- course number (unique per company)
- category
- vehicle_id (vehicle class)
- expiration date
- creation date
- examination date
- instructor_id (instructor class)
- person_id (person class)
- company_id (comapny class)
- financial report (not decided yet)
- driving practice sessions schedule

### Vehicle 
- id
- registration number
- kilometeres
- isFunctional
- isBroken
- model
- category ()
- fuel type
- consumption
- company (company class)
- daily statistics (a class maybe)

### Vehicle statistics
- id
- date
- kilometeres
- vehicle (Vehicle class)

