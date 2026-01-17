import { ViewWorkspaceSection } from "@/src/components/Workspace/ViewWorkspaceSection";

export default async function WorkspacePage(props: {
  params: Promise<{ workspaceId: number }>;
}) {
  const { workspaceId } = await props.params;
  return <ViewWorkspaceSection workspaceId={workspaceId} />;
}
