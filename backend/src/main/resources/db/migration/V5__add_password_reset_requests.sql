CREATE TABLE IF NOT EXISTS public.password_reset(
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    created_on TIMESTAMP NOT NULL,
    is_expired BOOLEAN NOT NULL,
    reset_id UUID NOT NULL,
    user_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES public.users(id)
);

CREATE INDEX idx_password_reset_user_id ON public.password_reset(user_id);