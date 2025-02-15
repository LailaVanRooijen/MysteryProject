CREATE TABLE IF NOT EXISTS public.courses
  (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    organization_id BIGINT,
    teacher_id UUID,
    CONSTRAINT fk_organization FOREIGN KEY (organization_id) REFERENCES public.organizations(id) ON DELETE SET NULL,
    CONSTRAINT fk_teacher FOREIGN KEY (teacher_id) REFERENCES public.users(id) ON DELETE SET NULL
  );

  CREATE TABLE IF NOT EXISTS public.course_students
  (
    course_id BIGINT,
    student_id UUID,
    PRIMARY KEY(course_id, student_id),
    CONSTRAINT fk_course FOREIGN KEY (course_id) REFERENCES public.courses(id) ON DELETE CASCADE,
    CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES public.users(id) ON DELETE CASCADE
  );