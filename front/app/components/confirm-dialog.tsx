import { useState } from "react";
import { Button } from "~/components/ui/button";
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "~/components/ui/dialog";

export function ConfirmDialog({
  title = "Êtes-vous sûr ?",
  description = "Cette action ne peut pas être annulée.",
  confirmText = "Confirmer",
  cancelText = "Annuler",
  onConfirm,
  children,
  disableButton = false,
  open,
  onOpenChange,
}: ConfirmDialogProps) {
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  const handleConfirm = () => {
    if (onConfirm) {
      onConfirm();
    }
    setIsDialogOpen(false);
  };

  return (
    <Dialog
      open={open ?? isDialogOpen}
      onOpenChange={onOpenChange ?? setIsDialogOpen}
    >
      <DialogTrigger asChild>{children}</DialogTrigger>
      <DialogContent className="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>{title}</DialogTitle>
          <DialogDescription>{description}</DialogDescription>
        </DialogHeader>
        <DialogFooter className="gap-2 sm:gap-0">
          <DialogClose asChild>
            <Button variant="outline" disabled={disableButton}>
              {cancelText}
            </Button>
          </DialogClose>
          <Button onClick={handleConfirm} disabled={disableButton}>
            {confirmText}
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
}

type ConfirmDialogProps = {
  title?: string;
  description?: string;
  confirmText?: string;
  cancelText?: string;
  onConfirm?: () => void;
  disableButton?: boolean;
  children?: React.ReactNode;
  open?: boolean;
  onOpenChange?: (b: boolean) => void;
};
