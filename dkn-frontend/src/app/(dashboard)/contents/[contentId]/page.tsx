import { ViewContentSection } from "@/src/components/Content/ViewContentSection";

export default async function ContentPage(props: {
  params: Promise<{ contentId: number }>;
}) {
  const { contentId } = await props.params;
  return <ViewContentSection contentId={contentId} />;
}
